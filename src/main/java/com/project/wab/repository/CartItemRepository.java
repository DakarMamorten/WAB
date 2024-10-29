package com.project.wab.repository;

import com.project.wab.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author "Vladyslav Paun"
 */
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
