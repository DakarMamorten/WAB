package com.project.wab.service;

import com.project.wab.domain.Product;
import com.project.wab.dto.ProductDTO;
import com.project.wab.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    @Value("${image.upload.dir}")
    private String uploadDir;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void saveProductWithImage(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setBrand(productDTO.getBrand());
        product.setImagePath("");
        productRepository.save(product);

        String imagePath = saveFile(productDTO.getImageFile(), product.getId());
        product.setImagePath(imagePath);

        productRepository.save(product);
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

            String sanitizedFileName = productId + "_" + file.getOriginalFilename().replaceAll("[^a-zA-Z0-9._-]", "_");
            Path filePath = uploadPath.resolve(sanitizedFileName);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            return sanitizedFileName;
        } catch (IOException e) {
            log.error("Error saving file: {}" , e.getMessage());
            return null;
        }
    }
}

