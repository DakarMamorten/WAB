package com.project.wab.service;

import com.project.wab.domain.Product;
import com.project.wab.dto.ProductDTO;
import com.project.wab.mapper.ProductMapper;
import com.project.wab.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = "ProductCache")
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductBrandService productBrandService;
    private final ProductMapper productMapper;

    @Value("${image.upload.dir}")
    private String uploadDir;

    @Cacheable(key = "'allProducts'")
    public List<Product> getAllProducts() {
        return productRepository.findAllWithBrands();
    }

    public List<Product> getProductsByBrandIds(List<Long> brandIds) {
        if (brandIds != null && !brandIds.isEmpty()) {
            return productRepository.findByProductBrandIdIn(brandIds);
        } else {
            return productRepository.findAll();
        }
    }

    @Cacheable(key = "#id")
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @CacheEvict(allEntries = true)
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @CacheEvict(key = "#id")
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void saveProductWithImage(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        var productBrand = productBrandService.getProductBrandById(productDTO.getBrandId());
        product.setProductBrand(productBrand);
        product.setImagePath("");
        productRepository.save(product);

        String imagePath = saveFile(productDTO.getImageFile(), product.getId());
        product.setImagePath(imagePath);

    }

    private String saveFile(MultipartFile file, Long productId) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String sanitizedFileName = productId + "_" + Objects.requireNonNull(file.getOriginalFilename()).replaceAll("[^a-zA-Z0-9._-]", "_");
            Path filePath = uploadPath.resolve(sanitizedFileName);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            return sanitizedFileName;
        } catch (IOException e) {
            log.error("Error saving file: {}", e.getMessage());
            return null;
        }
    }

    @Cacheable(key = "'allProductDtos'")
    public List<ProductDTO> getAllProductsDto() {
        return productRepository.getAllProductsDto();
    }
}

