package com.project.wab.controller.product;

import com.project.wab.dto.ProductDTO;
import com.project.wab.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author "Vladyslav Paun"
 */

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ProductAddController {

    private final ProductService productService;

    @GetMapping("/product/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        return "product/list";
    }

    @PostMapping("/product/add")
    public String addProduct(@ModelAttribute ProductDTO productDTO) {
        productService.saveProductWithImage(productDTO);
        return "redirect:/product/list";
    }
}
