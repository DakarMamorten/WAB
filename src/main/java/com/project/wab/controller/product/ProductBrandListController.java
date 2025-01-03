package com.project.wab.controller.product;

import com.project.wab.dto.ProductDTO;
import com.project.wab.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/product-brand")
public class ProductBrandListController {
    private final ProductService productService;
    @PostMapping("/{id}")
    public String showProductList(List<Long> ids, Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("products", productService.getAllProducts());
        return "product/list";
    }

}
