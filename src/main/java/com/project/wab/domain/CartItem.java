package com.project.wab.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author "Vladyslav Paun"
 */
@Entity
@Getter
@Setter
@Table(name = "cart_item")
public class CartItem {
    @EmbeddedId
    private CartItemId id;

    @Column(nullable = false)
    private int quantity;

    @MapsId("cartId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @MapsId("productId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
