package com.project.wab.controller.product;

import com.project.wab.domain.Product;
import com.project.wab.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductEditController {
    private final ProductService productService;

    @PostMapping("/edit")
    public String edit(final Long id, final String name, final String description, final BigDecimal price, final String brand) {
        Product product = productService.getProductById(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        productService.saveProduct(product);
        return "redirect:/product/list";
    }
}
