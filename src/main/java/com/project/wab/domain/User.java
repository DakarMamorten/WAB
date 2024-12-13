package com.project.wab.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @author "Vladyslav Paun"
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String role;
    @OneToMany(mappedBy = "user")
    private List<CartItem> cartItems;
}
