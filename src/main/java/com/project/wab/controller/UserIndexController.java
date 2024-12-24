package com.project.wab.controller;

import com.project.wab.domain.Cart;
import com.project.wab.domain.CartItem;
import com.project.wab.domain.User;
import com.project.wab.service.CartService;
import com.project.wab.service.ProductService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/")
public class UserIndexController {
    private final ProductService productService;
    private final CartService cartService;

    @GetMapping("/")
    public String userIndex(Model model, HttpServletRequest request, HttpServletResponse response) {
        String token = Arrays.stream(request.getCookies())
                .filter(cookie -> "anonymousUserToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if(user != null){
//            //user: Jan Kowalski
//            //cart: bf7daa6c-92df-42e9-b2e1-151a7c499b6e
//            //db cart: e0cf8529-44dc-4ab6-849b-68a2eb5c31b3
//        }else {
//            //anonym user
//            //anonym cart
//        }
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
