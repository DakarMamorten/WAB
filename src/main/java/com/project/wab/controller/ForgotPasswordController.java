package com.project.wab.controller;

import com.project.wab.service.PasswordResetTokenService;
import com.project.wab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequestMapping("/forgot-password")
@RequiredArgsConstructor
public class ForgotPasswordController {
    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;


    @GetMapping
    public String forgotPassword() {
        return "/forgot-password";
    }

    @PostMapping
    public String doForgotPassword(String email, Model model) {
        if (!userService.userIsExist(email)) {
            model.addAttribute("usernameError", "User with email " + email + " doesn't exist");
            return "/forgot-password";
        }
        userService.createPasswordResetTokenForUser(email);
        return "/login";
    }

    @GetMapping("/{token}")
    public String resetPassword(final @PathVariable("token") String token, Model model) {
        if (passwordResetTokenService.validate(token)) {
            model.addAttribute("userId", passwordResetTokenService.getUserIdByToken(token));
            return "/reset-password";
        }
        return "/login";
    }

    @PostMapping("/reset-password")
    public String savePassword(Long userId, String password) {
        userService.changePassword(userId, password);
        passwordResetTokenService.deleteToken(userId);
        return "redirect:/login";
    }
}
