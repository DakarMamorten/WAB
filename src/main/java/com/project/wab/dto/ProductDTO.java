package com.project.wab.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

/**
 * @author "Vladyslav Paun"
 */
@Data
public class ProductDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private String brand;
    private MultipartFile imageFile;
}
