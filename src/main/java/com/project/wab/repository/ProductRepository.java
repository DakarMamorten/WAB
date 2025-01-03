package com.project.wab.repository;

import com.project.wab.domain.Product;
import com.project.wab.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author "Vladyslav Paun"
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String s);

    @Query(value = "select p from Product p join fetch ProductBrand pb on p.productBrand.id = pb.id")
    List<Product> findAllWithBrands();

    List<Product> findByProductBrandIdIn(List<Long> brandIds);

    @Query("""
                SELECT new com.project.wab.dto.ProductDTO(
                    p.id,
                    p.name,
                    p.description,
                    p.price,
                    pb.name,
                    pb.id,
                    p.imagePath
                )
                FROM Product p
                JOIN p.productBrand pb
            """)
    List<ProductDTO> getAllProductsDto();
}
