package com.project.wab.repository;

import com.project.wab.domain.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * @author "Vladyslav Paun"
 */
public interface ProductRepository extends CrudRepository<Product, Long> {
}
