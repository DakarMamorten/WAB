package com.project.wab.repository;

import com.project.wab.domain.order.Order;
import com.project.wab.dto.OrderWithItemsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserId(Long user);

    @Query("""
            SELECT new com.project.wab.dto.OrderWithItemsProjection(
                CONCAT('', o.id),
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

}
