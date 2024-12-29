package com.project.wab.service;

import com.project.wab.domain.Product;
import com.project.wab.dto.ProductDTO;
import com.project.wab.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

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
            String uploadDir = System.getProperty("user.dir") + "/product-images";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = productId + "_" + file.getOriginalFilename() ;
            File uploadFile = new File(dir, fileName);
            file.transferTo(uploadFile);

            return "/product-images/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

