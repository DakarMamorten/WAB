package com.project.wab.service;

import com.project.wab.domain.ProductBrand;
import com.project.wab.repository.ProductBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "ProductBrand")
public class ProductBrandService {
    private final ProductBrandRepository productBrandRepository;

    @Cacheable(key = "'allProductBrand'")
    public List<ProductBrand> getAllProductBrand() {
        return productBrandRepository.findAll();
    }

    @Cacheable(key = "#id")
    public ProductBrand getProductBrandById(Long id) {
        return productBrandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product brand not found"));
    }

    @CacheEvict(allEntries = true)
    public void saveProductBrand(ProductBrand productBrand) {
        productBrandRepository.save(productBrand);
    }

    @CacheEvict(key = "#id")
    public void deleteProductBrand(Long id) {
        productBrandRepository.deleteById(id);
    }
}
