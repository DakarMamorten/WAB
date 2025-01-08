package com.project.wab.controller.order;

import com.project.wab.domain.order.Order;
import com.project.wab.domain.order.PaymentState;
import com.project.wab.domain.order.ShipmentState;
import com.project.wab.dto.RevenueReportDTO;
import com.project.wab.dto.TopProductDTO;
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

    @GetMapping("/orders")
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);

        model.addAttribute("shipmentStates", ShipmentState.values());
        model.addAttribute("paymentStates", PaymentState.values());
        return "/order/admin-orders";
    }

    @PostMapping("/update-payment")
    public String updateOrderStatus(UUID orderId, String newStatus) {
        orderService.updatePaymentStatus(orderId, newStatus);
        return "redirect:/admin/orders";
    }

    @PostMapping("/update-shipment")
    public String updateShipmentStatus(UUID orderId, String newStatus) {
        orderService.updateShipmentStatus(orderId, newStatus);
        return "redirect:/admin/orders";
    }

    @GetMapping("/reports")
    public String getReports(Model model) {
        List<TopProductDTO> topProducts = orderService.getTopSellingProducts(5);
        RevenueReportDTO revenueReport = orderService.getRevenueReportForPeriod();

        model.addAttribute("topProducts", topProducts);
        model.addAttribute("revenueReport", revenueReport);
        return "/reports/report";
    }
}
