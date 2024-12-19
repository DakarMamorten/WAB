package com.project.wab.controller.order;

import com.project.wab.domain.Order;
import com.project.wab.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderListController {
    private final OrderService orderService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Order> orders = orderService.findAll();
        if (orders.isEmpty()) {
            orders = new ArrayList<>();
        }
        model.addAttribute("orders", orders);
        return "order/list";
    }
}

