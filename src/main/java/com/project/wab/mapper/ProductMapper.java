package com.project.wab.mapper;

import com.project.wab.domain.Product;
import com.project.wab.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author "Vladyslav Paun"
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "brandName", target = "productBrand.name")
    @Mapping(source = "brandId", target = "productBrand.id")
    Product productDtoToProduct(ProductDTO source);

    @Mapping(source = "productBrand.name", target = "brandName")
    @Mapping(source = "productBrand.id", target = "brandId")
    ProductDTO productToProductDto(Product destination);
}
