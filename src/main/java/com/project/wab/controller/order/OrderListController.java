package com.project.wab.controller.order;

import com.project.wab.domain.User;
import com.project.wab.domain.order.Order;
import com.project.wab.service.user.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/user/order")
public class OrderListController {
    private final OrderService orderService;

    @GetMapping("/list")
    public String list(@CurrentSecurityContext SecurityContext context,
                       Model model) {
        Object principal = context.getAuthentication().getPrincipal();
        if (principal.equals("anonymousUser")) {
            model.addAttribute("user_orders", Collections.emptyList());
            return "redirect:/";
        }else{
            User currentUser = (User) context.getAuthentication().getPrincipal();
            List<Order> ordersByUserID = orderService.findOrdersByUserID(currentUser.getId());
            model.addAttribute("user_orders", ordersByUserID);
            return "order/user_list";
        }
    }
}

