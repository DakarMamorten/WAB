package com.project.wab.repository;

import com.project.wab.domain.User;
import com.project.wab.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */
public interface OrderRepository  extends JpaRepository<Order, UUID> {
    List<Order> findByUserId(Long user);

}
