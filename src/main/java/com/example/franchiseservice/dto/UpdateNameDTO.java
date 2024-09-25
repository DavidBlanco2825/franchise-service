package com.example.franchiseservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.franchiseservice.commons.Constants.NAME_IS_REQUIRED;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNameDTO {

    @NotBlank(message = NAME_IS_REQUIRED)
    private String name;
}
