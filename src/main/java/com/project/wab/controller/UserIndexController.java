package com.project.wab.controller;

import com.project.wab.service.CartService;
import com.project.wab.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String userIndex(@CurrentSecurityContext SecurityContext context,
                            Model model,
                            HttpServletRequest request,
                            HttpServletResponse response) {
//        final String userEmailLogin = context.getAuthentication().getName();

//        var token = WebUtil.checkToken(request);

//        var cart = cartService.getCartByToken(token);
//        if (!cart.getToken().equals(token)) {
//            final Cookie cookie = WebUtil.populateCookie(cart.getToken());
//            response.addCookie(cookie);
//        }
//        List<CartItem> cartItems = cart.getItems();

        var products = productService.getAllProducts();
        model.addAttribute("products", products);
//        int totalQuantity = cartItems.stream()
//                .mapToInt(CartItem::getQuantity)
//                .sum();

        model.addAttribute("cartSize", 0);

        return "user_index";
    }
}
