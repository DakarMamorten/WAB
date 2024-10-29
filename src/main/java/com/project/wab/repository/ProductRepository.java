package com.project.wab.repository;

import com.project.wab.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author "Vladyslav Paun"
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}
