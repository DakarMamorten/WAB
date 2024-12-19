package com.project.wab.controller.user;

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
public class UserDeleteController {
    private final UserService userService;

    @PostMapping("/delete")
    public String delete(final Long id) {
        userService.deleteUser(id);
        return "redirect:/user/list";
    }
}

