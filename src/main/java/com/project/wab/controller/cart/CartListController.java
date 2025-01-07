package com.project.wab.controller.cart;

import com.project.wab.service.CartService;
import com.project.wab.utils.CartHelper;
import com.project.wab.utils.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartListController {
    private final CartService cartService;

    @GetMapping("/list")
    public String showCart(Model model, HttpServletRequest request) {
        var cartToken = WebUtil.checkToken(request);

        if (cartToken == null || cartToken.isBlank()) {
            model.addAttribute("cartItems", List.of());
            model.addAttribute("totalPrice", BigDecimal.ZERO);
            return "cart/list";
        }

        try {
            var cartId = UUID.fromString(cartToken);
            var cartItems = cartService.getCartItemsDto(cartId);

            model.addAttribute("cartId", cartId);
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("totalPrice", CartHelper.calculateCartTotal(cartItems));
            model.addAttribute("cartSize",cartItems.size());
            return "cart/list";

        } catch (IllegalArgumentException e) {
            model.addAttribute("cartItems", List.of());
            model.addAttribute("totalPrice", BigDecimal.ZERO);

            return "cart/list";
        }
    }

}

