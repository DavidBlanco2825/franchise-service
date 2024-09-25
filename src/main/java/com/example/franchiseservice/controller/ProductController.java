package com.example.franchiseservice.controller;

import com.example.franchiseservice.dto.UpdateNameDTO;
import com.example.franchiseservice.dto.product.ProductRequestDTO;
import com.example.franchiseservice.dto.product.ProductResponseDTO;
import com.example.franchiseservice.dto.product.ProductWithBranch;
import com.example.franchiseservice.dto.product.UpdateStockDTO;
import com.example.franchiseservice.exception.ErrorResponse;
import com.example.franchiseservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/products")
@AllArgsConstructor
@Tag(name = "ProductController", description = "Operations related to product management")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/branch/{branchId}")
    @Operation(summary = "Add Product to Branch", description = "Adds a new product to a specific branch.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product successfully created.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid product data.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Mono<ResponseEntity<ProductResponseDTO>> addProductToBranch(
            @PathVariable @Parameter(description = "ID of the branch to add the product to", required = true, example = "1") Long branchId,
            @RequestBody @Parameter(description = "Product data for the new product", required = true) @Valid ProductRequestDTO productRequestDTO) {
        log.info("Received request to add a product to branch ID: {}", branchId);
        return productService.addProductToBranch(branchId, productRequestDTO)
                .map(createdProduct -> {
                    log.info("Product successfully added to branch ID: {}", branchId);
                    return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
                })
                .doOnError(error -> log.error("Failed to add product to branch ID {}: {}", branchId, error.getMessage()));
    }

    @PutMapping("/{productId}/stock")
    @Operation(summary = "Update Product Stock", description = "Updates the stock of a product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock successfully updated.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Mono<ResponseEntity<ProductResponseDTO>> updateProductStock(
            @PathVariable @Parameter(description = "ID of the product", required = true, example = "1") Long productId,
            @RequestBody @Parameter(description = "New stock value", required = true) @Valid UpdateStockDTO updateStockDTO) {
        log.info("Received request to update stock for product ID: {}", productId);
        return productService.updateProductStock(productId, updateStockDTO)
                .map(updatedProduct -> {
                    log.info("Stock successfully updated for product ID: {}", productId);
                    return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
                })
                .doOnError(error -> log.error("Failed to update stock for product ID {}: {}", productId, error.getMessage()));
    }

    @PutMapping("/{productId}/name")
    @Operation(summary = "Update Product Name", description = "Updates the name of a product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Name successfully updated.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Mono<ResponseEntity<ProductResponseDTO>> updateProductName(
            @PathVariable @Parameter(description = "ID of the product", required = true, example = "1") Long productId,
            @RequestBody @Parameter(description = "New name for the product", required = true) @Valid UpdateNameDTO updateNameDTO) {
        log.info("Received request to update product name for product ID: {}", productId);
        return productService.updateProductName(productId, updateNameDTO)
                .map(updatedProduct -> {
                    log.info("Product name successfully updated for product ID: {}", productId);
                    return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
                })
                .doOnError(error -> log.error("Failed to update product name for product ID {}: {}", productId, error.getMessage()));
    }

    @GetMapping("/highestStock/franchise/{franchiseId}")
    @Operation(summary = "Get Products with Highest Stock per Branch", description = "Displays the product with the highest stock per branch for a specific franchise.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products successfully retrieved.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductWithBranch.class))),
            @ApiResponse(responseCode = "404", description = "Franchise not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Flux<ProductWithBranch> getProductsWithHighestStockPerBranch(
            @PathVariable @Parameter(description = "ID of the franchise", required = true, example = "1") Long franchiseId) {
        log.info("Received request to fetch products with the highest stock per branch for franchise ID: {}", franchiseId);
        return productService.getProductsWithHighestStockPerBranch(franchiseId)
                .doOnComplete(() -> log.info("Successfully retrieved products with the highest stock per branch for franchise ID: {}", franchiseId))
                .doOnError(error -> log.error("Failed to retrieve products for franchise ID {}: {}", franchiseId, error.getMessage()));
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Remove Product from Branch", description = "Removes a product from a branch.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product successfully removed."),
            @ApiResponse(responseCode = "404", description = "Product not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Mono<ResponseEntity<Object>> removeProductFromBranch(
            @PathVariable @Parameter(description = "ID of the product", required = true, example = "1") Long productId) {
        log.info("Received request to remove product with product ID: {}", productId);
        return productService.removeProductFromBranch(productId)
                .then(Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).build()))
                .doOnSuccess(aVoid -> log.info("Product successfully removed with product ID: {}", productId))
                .doOnError(error -> log.error("Failed to remove product with product ID {}: {}", productId, error.getMessage()));
    }
}
