package com.project.wab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author "Vladyslav Paun"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private String productName;
    private BigDecimal price;
    private int quantity;
    private BigDecimal total;
    private Long productId;
}
