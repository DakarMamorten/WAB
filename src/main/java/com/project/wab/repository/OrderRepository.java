package com.project.wab.repository;

import com.project.wab.domain.order.Order;
import com.project.wab.dto.OrderWithItemsProjection;
import com.project.wab.dto.TopProductDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserId(Long user);

    @Query("""
            SELECT new com.project.wab.dto.OrderWithItemsProjection(
                CONCAT('', o.id),
                o.userEmail,
                o.state,
                o.paymentState,
                o.shipmentState,
                o.totalPrice,
                p.name,
                i.product.price,
                i.quantity,
                p.id,
                p.imagePath
            )
            FROM Order o
              JOIN o.items i
              JOIN i.product p
            WHERE o.id = :orderId
            """)
    List<OrderWithItemsProjection> findOrderWithItems(@Param("orderId") UUID orderId);

    @Query("SELECT new com.project.wab.dto.TopProductDTO(p.name, SUM(oi.quantity)) " +
            "FROM OrderItem oi JOIN oi.product p " +
            "GROUP BY p.name " +
            "ORDER BY SUM(oi.quantity) DESC")
    List<TopProductDTO> findTopSellingProducts(Pageable pageable);

    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    BigDecimal calculateRevenueBetweenDates(@Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    long countOrdersBetweenDates(@Param("startDate") LocalDateTime startDate,
                                 @Param("endDate") LocalDateTime endDate);

    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.user.id = :userId WHERE o.userEmail = :email AND o.user IS NULL")
    void bindOrdersToUser(@Param("userId") Long userId, @Param("email") String email);
}
