package com.project.wab.controller.product;

import com.project.wab.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductListController {
    private final ProductService productService;
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product/list";
    }

}
