package com.project.wab.repository;

import com.project.wab.domain.Order;
import org.springframework.data.repository.CrudRepository;

/**
 * @author "Vladyslav Paun"
 */
public interface OrderRepository  extends CrudRepository<Order, Long> {
}
