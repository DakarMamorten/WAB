package com.project.wab.controller;

import com.project.wab.domain.Product;
import com.project.wab.service.CartItemService;
import com.project.wab.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartItemController {
    private final ProductService productService;

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "add_product";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/list_products";
    }

}
