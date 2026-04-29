package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/")
    public String home() {
        return "login";
    }
    
    // Login page
    @GetMapping("/loginpage")
    public String loginPage() {
        return "login";
    }
    
    // Register page
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
    // Register user
    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        service.register(user);
        return "redirect:/"; // back to login
    }

    // Login check
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model) {

        System.out.println("LOGIN CALLED");

        User user = service.login(email, password);

        if (user != null) {
            return "home";
        } else {
            model.addAttribute("error", "Invalid Email or Password");
            return "login";
        }
    }
}