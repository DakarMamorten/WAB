package com.project.wab.repository;

import com.project.wab.domain.CartItem;
import com.project.wab.domain.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {

    @Query("SELECT SUM(ci.quantity) FROM CartItem ci WHERE ci.cart.id = :cartId")
    Integer totalProductByCartId(@Param("cartId") UUID cartId);

    @Query("SELECT SUM(ci.quantity) FROM CartItem ci JOIN Cart c ON ci.id.cartId = c.id WHERE c.userId = :userId")
    Integer totalProductByUserId(Long userId);

    @Query(value = "SELECT ci FROM CartItem ci WHERE ci.id.cartId = :cartId AND ci.id.productId = :productId")
    Optional<CartItem> findCartItemByCartIdAndProductId(UUID cartId, Long productId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId")
    List<CartItem> findAllByCartId(UUID cartId);

    @Query("SELECT ci FROM CartItem ci JOIN Cart c ON ci.id.cartId = c.id WHERE c.userId = :userId")
    List<CartItem> findAllByUserId(Long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM CartItem ci WHERE ci.id.cartId = :cartId")
    void deleteAllItemsById(UUID cartId);
}
