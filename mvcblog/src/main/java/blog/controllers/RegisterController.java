package blog.controllers;

import blog.services.PasswordService;
import blog.forms.RegistrationForm;
import blog.models.User;
import blog.services.NotificationService;
import blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;



@Controller
public class RegisterController {

    @Autowired
    private NotificationService notifyService;

    @Autowired
    private UserService userService;

    @Autowired
    PasswordService passwordService;

    @RequestMapping("/users/register")
    public String register(RegistrationForm registrationForm) {
        return "users/register";
    }

    @RequestMapping(value = "/users/register", method = RequestMethod.POST)
    public String registerPage(@Valid RegistrationForm registrationForm, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            notifyService.addErrorMessage("Please fill the form correctly!");
            return "users/register";
        }
        User user = new User(registrationForm.getUsername(), registrationForm.getFullName());
        user.setPasswordHash(passwordService.hash(registrationForm.getPassword().toCharArray()));
        userService.create(user);
        notifyService.addInfoMessage("Registration successful");
        return "redirect:/";
    }

}
