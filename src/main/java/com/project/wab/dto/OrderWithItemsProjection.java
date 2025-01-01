package com.project.wab.dto;

import com.project.wab.domain.order.OrderState;
import com.project.wab.domain.order.PaymentState;
import com.project.wab.domain.order.ShipmentState;

import java.math.BigDecimal;

/**
 * @author "Vladyslav Paun"
 */
public record OrderWithItemsProjection(
        String orderId,
        OrderState state,
        PaymentState paymentState,
        ShipmentState shipmentState,
        BigDecimal totalPrice,
        String productName,
        BigDecimal priceAtTimeOfOrder,
        int quantity,
        Long productId,
        String productImagePath
) {}
