package com.project.wab.controller.cart;

import com.project.wab.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartAddController {
    private final CartService cartService;

    @PostMapping("/add")
    public String add(final Long userId, final Long productId, final int quantity) {
        cartService.addProductToCart(userId, productId, quantity);
        return "redirect:/cart/list";
    }
}

