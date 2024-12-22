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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String role;
//    @OneToMany(mappedBy = "user")
//    private List<CartItem> cartItems;
}
