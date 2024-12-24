package com.project.wab.controller.cart;

import com.project.wab.domain.Cart;
import com.project.wab.domain.CartItem;
import com.project.wab.service.CartService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
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
    public String list(Model model, HttpServletRequest request) {
        String token = Arrays.stream(request.getCookies())
                .filter(cookie -> "anonymousUserToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Token not found"));

        Cart cart = cartService.getCartByToken(token);
        List<CartItem> cartItems = cart != null ? cart.getItems() : new ArrayList<>();

        model.addAttribute("cartItems", cartItems);
        return "cart/list";
    }
}

