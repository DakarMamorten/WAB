package com.project.wab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author "Vladyslav Paun"
 */
@Data
@AllArgsConstructor
public class TopProductDTO {
    private String productName;
    private Long totalQuantity;
}
