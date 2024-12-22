package com.project.wab.repository;

import com.project.wab.domain.Cart;
import com.project.wab.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
    Optional<Cart> findByUserId(Long id);
    Optional<Cart> findByToken(String token);

}
