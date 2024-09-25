package com.example.franchiseservice.service.impl;

import com.example.franchiseservice.dto.UpdateNameDTO;
import com.example.franchiseservice.dto.product.ProductRequestDTO;
import com.example.franchiseservice.dto.product.ProductResponseDTO;
import com.example.franchiseservice.dto.product.ProductWithBranch;
import com.example.franchiseservice.dto.product.UpdateStockDTO;
import com.example.franchiseservice.entity.Product;
import com.example.franchiseservice.exception.ResourceNotFoundException;
import com.example.franchiseservice.mapper.ProductMapper;
import com.example.franchiseservice.repository.BranchRepository;
import com.example.franchiseservice.repository.FranchiseRepository;
import com.example.franchiseservice.repository.ProductRepository;
import com.example.franchiseservice.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

import static com.example.franchiseservice.commons.Constants.BRANCH_NOT_FOUND_WITH_ID;
import static com.example.franchiseservice.commons.Constants.FRANCHISE_NOT_FOUND_WITH_ID;
import static com.example.franchiseservice.commons.Constants.PRODUCT_NOT_FOUND_WITH_ID;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final FranchiseRepository franchiseRepository;

    // Add a new product to a branch
    @Override
    public Mono<ProductResponseDTO> addProductToBranch(Long branchId, ProductRequestDTO productRequestDTO) {
        log.info("Adding product to branch ID: {}", branchId);
        return branchRepository.findById(branchId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(BRANCH_NOT_FOUND_WITH_ID + branchId)))
                .flatMap(branch -> {
                    Product product = ProductMapper.toEntity(productRequestDTO);
                    product.setBranchId(branchId);
                    return productRepository.save(product);
                })
                .map(ProductMapper::toResponseDto)
                .doOnSuccess(product -> log.info("Product added successfully to branch ID: {}", branchId))
                .doOnError(error -> log.error("Error adding product to branch ID {}: {}", branchId, error.getMessage()));
    }

    // Update the stock of a product
    @Override
    public Mono<ProductResponseDTO> updateProductStock(Long productId, UpdateStockDTO updateStockDTO) {
        log.info("Updating stock for product ID: {}", productId);
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(PRODUCT_NOT_FOUND_WITH_ID + productId)))
                .flatMap(product -> {
                    product.setStock(updateStockDTO.getStock());
                    return productRepository.save(product);
                })
                .map(ProductMapper::toResponseDto)
                .doOnSuccess(updatedProduct -> log.info("Stock updated successfully for product ID: {}", updatedProduct.getId()))
                .doOnError(error -> log.error("Error updating stock for product ID {}: {}", productId, error.getMessage()));
    }

    // Update the name of a product
    @Override
    public Mono<ProductResponseDTO> updateProductName(Long productId, UpdateNameDTO updateNameDTO) {
        log.info("Updating product name for product ID: {}", productId);
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(PRODUCT_NOT_FOUND_WITH_ID + productId)))
                .flatMap(product -> {
                    product.setName(updateNameDTO.getName());
                    return productRepository.save(product);
                })
                .map(ProductMapper::toResponseDto)
                .doOnSuccess(updatedProduct -> log.info("Product name updated successfully for product ID: {}", updatedProduct.getId()))
                .doOnError(error -> log.error("Error updating product name for product ID {}: {}", productId, error.getMessage()));
    }

    // Get the product with the highest stock per branch for a specific franchise
    @Override
    public Flux<ProductWithBranch> getProductsWithHighestStockPerBranch(Long franchiseId) {
        log.info("Fetching products with highest stock per branch for franchise ID: {}", franchiseId);
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(FRANCHISE_NOT_FOUND_WITH_ID + franchiseId)))
                .thenMany(branchRepository.findByFranchiseId(franchiseId)
                        .flatMap(branch -> productRepository.findByBranchId(branch.getId())
                                .collectList()
                                .flatMapMany(products -> {
                                    if (products.isEmpty()) {
                                        log.warn("No products found for branch ID: {}", branch.getId());
                                        return Flux.empty();
                                    }
                                    Product maxStockProduct = products.stream()
                                            .max(Comparator.comparingInt(Product::getStock))
                                            .orElse(null);
                                    log.info("Product with highest stock in branch ID {} is product ID: {}", branch.getId(), maxStockProduct.getId());
                                    return Flux.just(new ProductWithBranch(maxStockProduct, branch.getName()));
                                })
                        ))
                .doOnComplete(() -> log.info("Fetched all products with highest stock per branch for franchise ID: {}", franchiseId))
                .doOnError(error -> log.error("Error fetching products for franchise ID {}: {}", franchiseId, error.getMessage()));
    }

    // Remove a product from a branch
    @Override
    public Mono<Void> removeProductFromBranch(Long productId) {
        log.info("Removing product from branch with product ID: {}", productId);
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(PRODUCT_NOT_FOUND_WITH_ID + productId)))
                .flatMap(product -> productRepository.deleteById(productId))
                .doOnSuccess(aVoid -> log.info("Product removed successfully with product ID: {}", productId))
                .doOnError(error -> log.error("Error removing product with product ID {}: {}", productId, error.getMessage()));
    }
}
