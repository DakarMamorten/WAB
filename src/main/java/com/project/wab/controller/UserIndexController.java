package com.project.wab.controller;

import com.project.wab.domain.Cart;
import com.project.wab.service.CartService;
import com.project.wab.service.ProductService;
import com.project.wab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/index")
public class UserIndexController {
    private final ProductService productService;
    private final CartService cartService;

    @GetMapping("/user")
    public String userIndex(Model model) {
        var products= productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("cartSize", 0);
        return "user_index";
    }
}
