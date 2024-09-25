package com.example.franchiseservice.controller;

import com.example.franchiseservice.dto.UpdateNameDTO;
import com.example.franchiseservice.dto.branch.BranchRequestDTO;
import com.example.franchiseservice.dto.branch.BranchResponseDTO;
import com.example.franchiseservice.exception.ErrorResponse;
import com.example.franchiseservice.service.BranchService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/branches")
@AllArgsConstructor
@Tag(name = "BranchController", description = "Operations related to branch management")
public class BranchController {

    private final BranchService branchService;

    @PostMapping("/franchise/{franchiseId}")
    @Operation(summary = "Add Branch to Franchise", description = "Adds a new branch to a specific franchise.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Branch successfully created.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BranchResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid branch data.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Mono<ResponseEntity<BranchResponseDTO>> addBranchToFranchise(
            @PathVariable @Parameter(description = "ID of the franchise", required = true, example = "1") Long franchiseId,
            @RequestBody @Parameter(description = "Branch data for the new branch", required = true) @Valid BranchRequestDTO branchRequestDTO) {
        log.info("Received request to add a branch to franchise ID: {}", franchiseId);
        return branchService.addBranchToFranchise(franchiseId, branchRequestDTO)
                .map(createdBranch -> {
                    log.info("Branch successfully added to franchise ID: {}", franchiseId);
                    return ResponseEntity.status(HttpStatus.CREATED).body(createdBranch);
                })
                .doOnError(error -> log.error("Failed to add branch to franchise ID {}: {}", franchiseId, error.getMessage()));
    }

    @PutMapping("/{branchId}/name")
    @Operation(summary = "Update Branch Name", description = "Updates the name of an existing branch.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Name successfully updated.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BranchResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Branch not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Mono<ResponseEntity<BranchResponseDTO>> updateBranchName(
            @PathVariable @Parameter(description = "ID of the branch", required = true, example = "1") Long branchId,
            @RequestBody @Parameter(description = "New name for the branch", required = true) @Valid UpdateNameDTO updateNameDTO) {
        log.info("Received request to update branch name for branch ID: {}", branchId);
        return branchService.updateBranchName(branchId, updateNameDTO)
                .map(updatedBranch -> {
                    log.info("Branch name updated successfully for branch ID: {}", branchId);
                    return ResponseEntity.status(HttpStatus.OK).body(updatedBranch);
                })
                .doOnError(error -> log.error("Failed to update branch name for branch ID {}: {}", branchId, error.getMessage()));
    }
}
