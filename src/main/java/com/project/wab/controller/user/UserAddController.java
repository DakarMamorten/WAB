package com.project.wab.controller.user;

import com.project.wab.domain.User;
import com.project.wab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserAddController {
    private final UserService userService;

//    @PostMapping("/add")
//    public String add(final String name, final String email, final String password, final String role) {
//        User user = new User(null, name, email, password, role, new ArrayList<>());
//        userService.saveUser(user);
//        return "redirect:/user/list";
//    }
}
