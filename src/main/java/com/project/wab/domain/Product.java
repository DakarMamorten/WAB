package com.project.wab.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author "Vladyslav Paun"
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String brand;
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orders;

}
