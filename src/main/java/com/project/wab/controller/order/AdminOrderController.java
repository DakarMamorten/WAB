package com.project.wab.controller.order;

import com.project.wab.domain.order.Order;
import com.project.wab.domain.order.PaymentState;
import com.project.wab.service.user.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping
    @RequestMapping("/orders")
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "/order/admin-orders";
    }

    @PostMapping("/update")
    public String updateOrderStatus(UUID orderId) {
        orderService.updatePaymentStatus(orderId, PaymentState.PAID);
        return "redirect:/admin/orders";
    }
}
