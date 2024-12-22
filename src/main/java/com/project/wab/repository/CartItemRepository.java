package com.project.wab.repository;

import com.project.wab.domain.CartItem;
import com.project.wab.domain.Product;
import com.project.wab.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author "Vladyslav Paun"
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCartId(Long cartId);

}
