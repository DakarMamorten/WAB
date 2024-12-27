package com.project.wab.controller.cart;

import com.project.wab.service.CartService;
import com.project.wab.utils.WebUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public String addToCart(Long productId,
                            HttpServletRequest httpRequest,
                            HttpServletResponse response) {

        String token = WebUtil.checkToken(httpRequest);
        token = cartService.createAndPopulateCart(productId, token);
        Cookie cookie = WebUtil.populateCookie(token);
        response.addCookie(cookie);

        return "redirect:/";
    }

}

