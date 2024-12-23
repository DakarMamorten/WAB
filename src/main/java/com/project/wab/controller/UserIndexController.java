package com.project.wab.controller;

import com.project.wab.domain.Cart;
import com.project.wab.domain.CartItem;
import com.project.wab.service.CartService;
import com.project.wab.service.ProductService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping("/index")
public class UserIndexController {
    private final ProductService productService;
    private final CartService cartService;

    @GetMapping("/user")
    public String userIndex(Model model, HttpServletRequest request, HttpServletResponse response) {
        String token = Arrays.stream(request.getCookies())
                .filter(cookie -> "anonymousUserToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        Cart cart = cartService.getCartByToken(token);

        if (token == null) {
            token = cart.getToken();
            Cookie newCookie = new Cookie("anonymousUserToken", token);
            newCookie.setHttpOnly(true);
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(newCookie);
        }

        List<CartItem> cartItems = cart != null ? cart.getItems() : new ArrayList<>();

        var products = productService.getAllProducts();
        model.addAttribute("products", products);
        int totalQuantity = cartItems.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();

        model.addAttribute("cartSize", totalQuantity);

        return "user_index";
    }
}
