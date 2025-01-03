package com.project.wab.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

/**
 * @author "Vladyslav Paun"
 */
@Data
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String brandName;
    private Long brandId;
    private String imagePath;
    private MultipartFile imageFile;

    public ProductDTO(
            Long id,
            String name,
            String description,
            BigDecimal price,
            String brandName,
            Long brandId,
            String imagePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.brandName = brandName;
        this.brandId = brandId;
        this.imagePath = imagePath;
    }
}
