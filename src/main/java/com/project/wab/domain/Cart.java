package com.project.wab.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "expire_date")
    private LocalDateTime expireDate;

    @PrePersist
    @PreUpdate
    private void validateConstraints() {
        if (userId == null && expireDate == null) {
            throw new IllegalStateException("Either id or expireDate must be non-null.");
        }
        if (userId != null && expireDate != null) {
            throw new IllegalStateException("Both id and expireDate cannot be non-null simultaneously.");
        }
    }
}
