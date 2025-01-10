package com.project.wab.controller.order;

import com.project.wab.domain.User;
import com.project.wab.service.CartService;
import com.project.wab.service.user.OrderService;
import com.project.wab.utils.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;


/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/user/order")
public class OrderListController {
    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping("/list")
    public String list(@CurrentSecurityContext SecurityContext context,
                       Model model,
                       HttpServletRequest request) {
        Object principal = context.getAuthentication().getPrincipal();
        if (principal.equals("anonymousUser")) {
            model.addAttribute("user_orders", Collections.emptyList());
            return "redirect:/";
        } else {
            var currentUser = (User) context.getAuthentication().getPrincipal();
            orderService.bindOrdersToUser(currentUser.getEmail(), currentUser.getId());
            var ordersByUserID = orderService.findOrdersByUserID(currentUser.getId());
            model.addAttribute("user_orders", ordersByUserID);
            var cartToken = WebUtil.checkToken(request);
            var cartId = cartToken == null ? null : UUID.fromString(cartToken);
            final Map<String, Object> map = cartService.mergeAndDeleteCart(cartId, null);
            model.addAttribute("cartSize", map.get("p_total"));
            return "order/user_list";
        }
    }
}

