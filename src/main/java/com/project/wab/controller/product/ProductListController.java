package com.project.wab.controller.product;

import com.project.wab.dto.ProductDTO;
import com.project.wab.service.ProductBrandService;
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
@RequestMapping("/admin")
public class ProductListController {
    private final ProductService productService;
    private final ProductBrandService productBrandService;

    @GetMapping("/product/list")
    public String showProductList(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("products", productService.getAllProductsDto());
        model.addAttribute("productBrands", productBrandService.getAllProductBrand());
        return "product/list";
    }

}
