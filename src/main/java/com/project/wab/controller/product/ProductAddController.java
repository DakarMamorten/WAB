package com.project.wab.controller.product;

import com.project.wab.domain.Product;
import com.project.wab.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author "Vladyslav Paun"
 */

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductAddController {

    private final ProductService productService;

    @PostMapping("/add")
    public String add(final String name, final String description, final BigDecimal price, final String brand) {
        Product product = new Product(null, name, description, price, brand, new ArrayList<>());
        productService.saveProduct(product);
        return "redirect:/product/list";
    }
}
