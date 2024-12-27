package com.project.wab.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author "Vladyslav Paun"
 */
@Entity
@Getter
@Setter
public class CartItem {
    @EmbeddedId
    private CartItemId id;

    @Column(nullable = false)
    private int quantity;

    @MapsId("cartId")
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
