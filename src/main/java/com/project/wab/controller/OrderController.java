package com.project.wab.controller;

import com.project.wab.domain.Order;
import com.project.wab.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author "Vladyslav Paun"
 */

@Controller
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public Order saveOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
