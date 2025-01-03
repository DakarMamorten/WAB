package com.project.wab.repository;

import com.project.wab.domain.ProductBrand;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author "Vladyslav Paun"
 */
public interface ProductBrandRepository extends JpaRepository<ProductBrand, Long> {
}
