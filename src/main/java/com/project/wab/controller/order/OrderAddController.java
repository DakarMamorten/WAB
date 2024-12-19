package com.project.wab.controller.order;

import com.project.wab.domain.User;
import com.project.wab.service.OrderService;
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
@RequestMapping("/order")
public class OrderAddController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/add")
    public String add(final Long userId) {
        User user = userService.getUserById(userId);
        orderService.createOrder(user);
        return "redirect:/order/list";
    }
}
