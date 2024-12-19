package com.project.wab.controller.user;

import com.project.wab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserListController {
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user/list";
    }
}
