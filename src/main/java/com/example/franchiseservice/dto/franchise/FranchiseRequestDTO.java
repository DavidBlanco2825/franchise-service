package com.example.franchiseservice.dto.franchise;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.franquicias.commons.Constants.FRANCHISE_NAME_IS_REQUIRED;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseRequestDTO {

    @NotBlank(message = FRANCHISE_NAME_IS_REQUIRED)
    private String name;
}
