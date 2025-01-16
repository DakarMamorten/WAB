package com.project.wab.controller.order;

import com.project.wab.domain.order.Order;
import com.project.wab.domain.order.PaymentState;
import com.project.wab.domain.order.ShipmentState;
import com.project.wab.dto.RevenueReportDTO;
import com.project.wab.dto.TopProductDTO;
import com.project.wab.service.user.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
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
        orderService.updatePaymentStatus(orderId, PaymentState.valueOf(newStatus));
        return "redirect:/admin/orders";
    }

    @PostMapping("/update-shipment")
    public String updateShipmentStatus(@RequestParam UUID orderId,
                                       @RequestParam ShipmentState newShipmentState,
                                       RedirectAttributes redirectAttributes) {
        try {
            orderService.updateShipmentStatusAndNotify(orderId, newShipmentState);

            redirectAttributes.addFlashAttribute("message", "Shipment status updated and notification sent.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update shipment status: " + e.getMessage());
        }

        return "redirect:/admin/orders";
    }

    @GetMapping("/reports")
    public String getRevenueReport(@RequestParam(value = "startDate", required = false) LocalDateTime startDate,
                                   @RequestParam(value = "endDate", required = false) LocalDateTime endDate,
                                   Model model) {
        List<TopProductDTO> topProducts = orderService.getTopProducts(PageRequest.of(0, 5));
        RevenueReportDTO report = orderService.getRevenueReportForPeriod(startDate, endDate);

        model.addAttribute("topProducts", topProducts);
        model.addAttribute("report", report);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "/reports/report";
    }
}
