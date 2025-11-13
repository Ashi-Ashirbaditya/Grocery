package net.ads.grocery.controller;

import net.ads.grocery.dto.UserRegistration;
import net.ads.grocery.model.User;
import net.ads.grocery.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Registration form view
    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistration());
        return "registration";
    }

    // Handle user registration
    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") UserRegistration userRegistration, Model model) {
        try{User user = userService.save(userRegistration);
        model.addAttribute("success", true);
        return "registration";}
        catch(IllegalArgumentException e){
            model.addAttribute("error", e.getMessage());
            return "registration";
        }
    }


    // Login form view
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/user/profile")
    public String userProfile(Model model, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("roles", userDetails.getAuthorities());
        } else {
            model.addAttribute("username", "Guest");
            model.addAttribute("roles", "None");
        }
        return "userProfile";
    }
}

