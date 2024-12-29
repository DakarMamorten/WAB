package com.project.wab.domain.order;

import com.project.wab.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author "Vladyslav Paun"
 */
@Entity
@Getter
@Setter
public class OrderItem {
    @EmbeddedId
    private OrderItemId id;

    @Column(nullable = false)
    private int quantity;

    @MapsId("orderId")
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "price_at_time_of_order", nullable = false)
    private BigDecimal priceAtTimeOfOrder;

}
