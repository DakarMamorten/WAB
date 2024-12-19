package com.project.wab.controller.user;

import com.project.wab.domain.User;
import com.project.wab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserEditController {
    private final UserService userService;

    @PostMapping("/edit")
    public String edit(final Long id, final String name, final String email, final String password, final String role) {
        User user = userService.getUserById(id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        userService.saveUser(user);
        return "redirect:/user/list";
    }
}
