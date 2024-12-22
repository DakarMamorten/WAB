package com.project.wab.controller.cart;

import com.project.wab.domain.Cart;
import com.project.wab.dto.AddToCartRequest;
import com.project.wab.service.CartService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartAddController {
    private final CartService cartService;

//    @PostMapping("/add")
//    public String add(final Long userId, final Long productId, final int quantity) {
//        cartService.addProductToCart(userId, productId, quantity);
//        return "redirect:/cart/list";
//    }

    @PostMapping("/add")
    public String addToCart(@RequestBody AddToCartRequest request, HttpServletRequest httpRequest) {
        String token = Arrays.stream(httpRequest.getCookies())
                .filter(cookie -> "anonymousUserToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Token not found"));

        cartService.addItemToCart(token, request.productName(), request.quantity());
        return "redirect:/cart/list";
    }

    @GetMapping()
    public String viewCart(Model model, HttpServletRequest request) {
        String token = Arrays.stream(request.getCookies())
                .filter(cookie -> "anonymousUserToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        Cart cart = cartService.getCartByToken(token);
        model.addAttribute("cart", cart);
        return "cart/list";
    }
}

