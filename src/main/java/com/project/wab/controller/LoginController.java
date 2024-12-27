package com.project.wab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author "Vladyslav Paun"
 */

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        return "login";
    }

    @PostMapping("/login/error")
    public String handleLoginError(Model model) {
        model.addAttribute("error", "Invalid username or password. Please try again.");
        return "login";
    }
}