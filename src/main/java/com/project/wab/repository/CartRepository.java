package com.project.wab.repository;

import com.project.wab.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */

public interface CartRepository extends JpaRepository<Cart, UUID> {
    @Query(value = "SELECT c FROM Cart c WHERE c.userId = :userId")
    Cart findCartByUserID(Long userId);

    @Query(value = "SELECT c.id FROM Cart c WHERE c.userId = :userId")
    UUID findCartIdByUserID(Long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Cart c WHERE c.id = :cartId")
    void deleteCartById(UUID cartId);
}
