package com.example.franchiseservice.service.impl;

import com.example.franchiseservice.dto.UpdateNameDTO;
import com.example.franchiseservice.dto.product.ProductRequestDTO;
import com.example.franchiseservice.dto.product.ProductResponseDTO;
import com.example.franchiseservice.entity.Branch;
import com.example.franchiseservice.entity.Product;
import com.example.franchiseservice.exception.ResourceNotFoundException;
import com.example.franchiseservice.mapper.ProductMapper;
import com.example.franchiseservice.repository.BranchRepository;
import com.example.franchiseservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    private ProductRepository productRepository;
    private BranchRepository branchRepository;
    private ProductServiceImpl productService;

    private ProductRequestDTO productRequestDTO;
    private Product product;
    private ProductResponseDTO productResponseDTO;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        branchRepository = mock(BranchRepository.class);
        productService = new ProductServiceImpl(productRepository, branchRepository, null);

        productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName("New Product");

        product = new Product();
        product.setId(1L);
        product.setBranchId(1L);
        product.setName("New Product");

        productResponseDTO = ProductMapper.toResponseDto(product);
    }

    @Test
    void testAddProductToBranch_Success() {
        Branch branch = new Branch();
        branch.setId(1L);

        when(branchRepository.findById(anyLong())).thenReturn(Mono.just(branch));
        when(productRepository.save(any(Product.class))).thenReturn(Mono.just(product));

        Mono<ProductResponseDTO> result = productService.addProductToBranch(1L, productRequestDTO);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getName().equals("New Product") && response.getId().equals(1L))
                .verifyComplete();

        verify(branchRepository).findById(anyLong());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testAddProductToBranch_BranchNotFound() {
        when(branchRepository.findById(anyLong())).thenReturn(Mono.empty());

        Mono<ProductResponseDTO> result = productService.addProductToBranch(1L, productRequestDTO);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ResourceNotFoundException && throwable.getMessage().contains("Branch not found"))
                .verify();

        verify(branchRepository).findById(anyLong());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testUpdateProductName_Success() {
        when(productRepository.findById(anyLong())).thenReturn(Mono.just(product));
        when(productRepository.save(any(Product.class))).thenReturn(Mono.just(product));

        UpdateNameDTO updateNameDTO = new UpdateNameDTO();
        updateNameDTO.setName("Updated Product");

        Mono<ProductResponseDTO> result = productService.updateProductName(1L, updateNameDTO);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getName().equals("Updated Product"))
                .verifyComplete();

        verify(productRepository).findById(anyLong());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testUpdateProductName_ProductNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Mono.empty());

        UpdateNameDTO updateNameDTO = new UpdateNameDTO();
        updateNameDTO.setName("Updated Product");

        Mono<ProductResponseDTO> result = productService.updateProductName(1L, updateNameDTO);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ResourceNotFoundException && throwable.getMessage().contains("Product not found"))
                .verify();

        verify(productRepository).findById(anyLong());
        verify(productRepository, never()).save(any(Product.class));
    }
}
