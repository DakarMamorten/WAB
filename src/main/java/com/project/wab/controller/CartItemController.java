package com.project.wab.controller;

import com.project.wab.domain.Product;
import com.project.wab.service.CartItemService;
import com.project.wab.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartItemController {
    private final ProductService productService;
    private final CartItemService cartItemService;


    @GetMapping("/add")
    public String showAddToCartForm(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "add_to_cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId, Integer quantity) {
        cartItemService.addProductToCart(productId, quantity);
        return "redirect:/cart";
    }

}
