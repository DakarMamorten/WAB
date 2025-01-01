package com.project.wab.controller;

import com.project.wab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forgot-password")
@RequiredArgsConstructor
public class ForgotPasswordController {
    private final UserService userService;
//    private final PasswordResetTokenService passwordResetTokenService;


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
//
//    @GetMapping("/{token}")
//    public String resetPassword(final @PathVariable("token") String token, Model model) {
//        if (passwordResetTokenService.validate(token)) {
//            model.addAttribute("tl_pg_inline", "/admin/root/forgot/inline");
//            model.addAttribute("tl_pg_script", "/admin/root/forgot/script");
//            model.addAttribute("userId", passwordResetTokenService.getUserIdByToken(token));
//            return "/admin/root/forgot/change-password";
//        }
//        return "/admin/root/forgot/error-token";
//    }
//
//    @PostMapping("/user/resetPassword")
//    public String savePassword(
//            final @RequestParam("userId") Long userId,
//            final @RequestParam("password1") String password1) {
//        userService.changePassword(userId, password1);
//        passwordResetTokenService.deleteToken(userId);
//        return "redirect:/login/root";
//    }
}
