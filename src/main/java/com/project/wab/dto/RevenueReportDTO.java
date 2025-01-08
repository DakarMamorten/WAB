package com.project.wab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author "Vladyslav Paun"
 */
@Data
@AllArgsConstructor
public class RevenueReportDTO {
    private BigDecimal totalRevenue;
    private long totalOrders;
}
