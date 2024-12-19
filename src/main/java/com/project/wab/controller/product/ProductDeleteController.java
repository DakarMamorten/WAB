package com.project.wab.controller.product;

import com.project.wab.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductDeleteController {
    private final ProductService productService;

    @PostMapping("/delete")
    public String delete(final Long id) {
        productService.deleteProduct(id);
        return "redirect:/product/list";
    }
}
