package com.project.wab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author "Vladyslav Paun"
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String id;
    private String state;
    private String paymentState;
    private String shipmentState;
    private BigDecimal totalPrice;
    private List<OrderItemDTO> items;
}
