package com.project.wab.controller.cart;

import com.project.wab.domain.Cart;
import com.project.wab.domain.CartItem;
import com.project.wab.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartListController {
    private final CartService cartService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        List<CartItem> cartItems = cart != null ? cart.getItems() : new ArrayList<>();
        model.addAttribute("cartItems", cartItems);
        return "cart/list";
    }
}

