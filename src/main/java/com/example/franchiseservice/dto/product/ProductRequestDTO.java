package com.example.franchiseservice.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.franchiseservice.commons.Constants.PRODUCT_NAME_IS_REQUIRED;
import static com.example.franchiseservice.commons.Constants.PRODUCT_STOCK_IS_REQUIRED;
import static com.example.franchiseservice.commons.Constants.PRODUCT_STOCK_NEGATIVE;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    @NotBlank(message = PRODUCT_NAME_IS_REQUIRED)
    private String name;

    @NotNull(message = PRODUCT_STOCK_IS_REQUIRED)
    @Min(value = 0, message = PRODUCT_STOCK_NEGATIVE)
    private Integer stock;
}
