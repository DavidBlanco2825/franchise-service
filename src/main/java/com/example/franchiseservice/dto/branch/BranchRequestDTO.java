package com.example.franchiseservice.dto.branch;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.franchiseservice.commons.Constants.BRANCH_NAME_IS_REQUIRED;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchRequestDTO {

    @Schema(type = "string",
            description = "branch name",
            example =  "First branch")
    @NotBlank(message = BRANCH_NAME_IS_REQUIRED)
    private String name;
}
