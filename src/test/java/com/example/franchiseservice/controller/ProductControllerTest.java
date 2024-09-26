package com.example.franchiseservice.controller;

import com.example.franchiseservice.dto.UpdateNameDTO;
import com.example.franchiseservice.dto.product.ProductRequestDTO;
import com.example.franchiseservice.dto.product.ProductResponseDTO;
import com.example.franchiseservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = ProductController.class)
public class ProductControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductService productService;

    private ProductRequestDTO productRequestDTO;
    private ProductResponseDTO productResponseDTO;
    private UpdateNameDTO updateNameDTO;

    @BeforeEach
    void setUp() {
        productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName("New Product");

        productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(1L);
        productResponseDTO.setName("New Product");

        updateNameDTO = new UpdateNameDTO();
        updateNameDTO.setName("Updated Product");
    }

    @Test
    void testAddProductToBranch() {
        // Ensure the stock value is set in the request DTO
        productRequestDTO.setStock(100);  // Add valid stock value

        // Set the expected values in the response DTO
        productResponseDTO.setName("New Product");
        productResponseDTO.setStock(100);

        when(productService.addProductToBranch(anyLong(), any(ProductRequestDTO.class)))
                .thenReturn(Mono.just(productResponseDTO));

        webTestClient.post()
                .uri("/products/branch/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(productRequestDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ProductResponseDTO.class)
                .value(response -> {
                    assert response.getName().equals("New Product");
                    assert response.getId().equals(1L);
                    assert response.getStock().equals(100);  // Verify stock
                });

        Mockito.verify(productService).addProductToBranch(anyLong(), any(ProductRequestDTO.class));
    }

    @Test
    void testUpdateProductName() {
        productResponseDTO.setName("Updated Product");

        when(productService.updateProductName(anyLong(), any(UpdateNameDTO.class)))
                .thenReturn(Mono.just(productResponseDTO));

        webTestClient.put()
                .uri("/products/1/name")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateNameDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductResponseDTO.class)
                .value(response -> {
                    assert response.getName().equals("Updated Product");
                });

        Mockito.verify(productService).updateProductName(anyLong(), any(UpdateNameDTO.class));
    }

}
