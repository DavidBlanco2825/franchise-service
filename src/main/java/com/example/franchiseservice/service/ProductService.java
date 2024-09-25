package com.example.franchiseservice.service;

import com.example.franchiseservice.dto.UpdateNameDTO;
import com.example.franchiseservice.dto.product.ProductRequestDTO;
import com.example.franchiseservice.dto.product.ProductResponseDTO;
import com.example.franchiseservice.dto.product.ProductWithBranch;
import com.example.franchiseservice.dto.product.UpdateStockDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<ProductResponseDTO> addProductToBranch(Long branchId, ProductRequestDTO productRequestDTO);

    Mono<ProductResponseDTO> updateProductStock(Long productId, UpdateStockDTO updateStockDTO);

    Mono<ProductResponseDTO> updateProductName(Long productId, UpdateNameDTO updateNameDTO);

    Flux<ProductWithBranch> getProductsWithHighestStockPerBranch(Long franchiseId);

    Mono<Void> removeProductFromBranch(Long productId);
}
