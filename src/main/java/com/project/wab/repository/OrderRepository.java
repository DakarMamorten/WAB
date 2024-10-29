package com.project.wab.repository;

import com.project.wab.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author "Vladyslav Paun"
 */
public interface OrderRepository  extends JpaRepository<Order, Long> {
}
