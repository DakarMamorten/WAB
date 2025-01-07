package com.project.wab.repository;

import com.project.wab.domain.ProductBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author "Vladyslav Paun"
 */
@Repository
public interface ProductBrandRepository extends JpaRepository<ProductBrand, Long> {
}
