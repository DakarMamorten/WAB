package com.project.wab.controller;

import com.project.wab.domain.Cart;
import com.project.wab.domain.CartItem;
import com.project.wab.service.CartService;
import com.project.wab.service.ProductService;
import com.project.wab.service.UserService;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/index")
public class UserIndexController {
    private final ProductService productService;
    private final CartService cartService;

    @GetMapping("/user")
    public String userIndex(Model model, HttpServletRequest request) {
        String token = Arrays.stream(request.getCookies())
                .filter(cookie -> "anonymousUserToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Token not found"));

        Cart cart = cartService.getCartByToken(token);
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
