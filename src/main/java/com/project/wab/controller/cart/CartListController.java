package com.project.wab.controller.cart;

import com.project.wab.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartListController {
    private final CartService cartService;

    @GetMapping("/list")
    public String showCart() {
        return "cart/list";
    }
}

