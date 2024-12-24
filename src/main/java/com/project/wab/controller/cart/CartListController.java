package com.project.wab.controller.cart;

import com.project.wab.domain.Cart;
import com.project.wab.domain.CartItem;
import com.project.wab.service.CartService;
import com.project.wab.utils.WebUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

//    @GetMapping("/list")
//    public String list(Model model, HttpServletRequest request) {
//        String token = WebUtil.checkToken(request);
//
//        Cart cart = cartService.getCartByToken(token);
//        List<CartItem> cartItems = cart.getItems();
//
//        model.addAttribute("cartItems", cartItems);
//        return "cart/list";
//    }
}

