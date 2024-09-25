package com.example.franchiseservice.mapper;

import com.example.franchiseservice.dto.product.ProductRequestDTO;
import com.example.franchiseservice.dto.product.ProductResponseDTO;
import com.example.franchiseservice.entity.Product;

public class ProductMapper {

    public static ProductResponseDTO toResponseDto(Product product) {

        if (product == null) {
            return null;
        }

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getBranchId()
        );
    }

    public static Product toEntity(ProductRequestDTO productRequestDTO) {
        if (productRequestDTO == null) {
            return null;
        }

        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setStock(productRequestDTO.getStock());

        return product;
    }
}
