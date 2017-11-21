package blog.controllers;


import blog.models.Post;
import blog.models.User;
import blog.services.NotificationService;
import blog.services.PostService;
import blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UsersController {

    @Autowired
    UserService userService;

    @Autowired
    private NotificationService notifyService;

    @Autowired
    PostService postService;

    @RequestMapping("/users/userlist")
    public String postsList(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        if (users == null) {
            notifyService.addErrorMessage("Users not found");
            return "redirect:/";
        }
        return "users/userlist";
    }

    @RequestMapping("/posts/userposts/{id}")
    public String userPosts(@PathVariable("id") Long id, Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        if (posts == null) {
            notifyService.addErrorMessage("Posts not found");
            return "redirect:/";
        }
        return "posts/userposts";
    }
}