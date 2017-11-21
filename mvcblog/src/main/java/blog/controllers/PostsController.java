package blog.controllers;


import blog.forms.LoginForm;
import blog.forms.PostCreationForm;
import blog.models.Post;
import blog.services.LoginService;
import blog.services.NotificationService;
import blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.validation.Valid;
import java.util.List;

@Controller
public class PostsController {
    @Autowired
    private PostService postService;

    @Autowired
    private NotificationService notifyService;

    @Autowired
    private LoginService loginService;

    @RequestMapping("/posts/view/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        Post post = postService.findById(id);
        if (post == null) {
            notifyService.addErrorMessage("Cannot find post #" + id);
            return "redirect:/";
        }
        model.addAttribute("post", post);
        return "posts/view";
    }

    @RequestMapping("/posts/list")
    public String postsList(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        if (posts == null) {
            notifyService.addErrorMessage("Posts not found");
            return "redirect:/";
        }
        return "posts/list";
    }

    @RequestMapping("/posts/create")
    public String createPost(@Valid PostCreationForm postCreationForm, BindingResult bindingResult) {
        if (loginService.getUser() == null) {
            notifyService.addErrorMessage("Unregistered user");
            return "redirect:/";
        }
        if (bindingResult.hasErrors()) {
            notifyService.addErrorMessage("Empty post");
        }
        if (postCreationForm.getTitle() != null && postCreationForm.getText() != null) {
            Post post = new Post(postCreationForm.getTitle(),
                    postCreationForm.getText(), loginService.getUser());
            postService.create(post);
        }
        return "posts/create";

    }

    @RequestMapping("/posts/delete/{id}")
    public String view(@PathVariable("id") Long id) {
        postService.deleteById(id);
        return "posts/delete";
    }

    @RequestMapping("/posts/edit/{id}")
    public String editPost(@Valid PostCreationForm postCreationForm,
                           BindingResult bindingResult, @PathVariable("id") Long id) {
        if (loginService.getUser() == null) {
            notifyService.addErrorMessage("Unregistered user");
            return "redirect:/";
        }
       /* if (postService.findById(id).getAuthor() != loginService.getUser()) {
            notifyService.addErrorMessage("Not your post");
            return "redirect:/";
        }*/
        if (bindingResult.hasErrors()) {
            notifyService.addErrorMessage("Empty post");
            return "posts/edit";
        }
        postService.deleteById(id);
        if (postCreationForm.getTitle() != null && postCreationForm.getText() != null) {
            Post post = new Post(id, postCreationForm.getTitle(),
                    postCreationForm.getText(), loginService.getUser());
            postService.edit(post);
        }
        return "posts/list";
    }
}
