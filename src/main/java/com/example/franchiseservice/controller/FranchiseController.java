package com.example.franchiseservice.controller;

import com.example.franchiseservice.dto.UpdateNameDTO;
import com.example.franchiseservice.dto.franchise.FranchiseRequestDTO;
import com.example.franchiseservice.dto.franchise.FranchiseResponseDTO;
import com.example.franchiseservice.exception.ErrorResponse;
import com.example.franchiseservice.service.FranchiseService;
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
@RequestMapping("/franchises")
@AllArgsConstructor
@Tag(name = "FranchiseController", description = "Operations related to franchise management")
public class FranchiseController {

    private final FranchiseService franchiseService;

    @PostMapping
    @Operation(summary = "Add a New Franchise", description = "Creates a new franchise.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Franchise successfully created.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FranchiseResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Mono<ResponseEntity<FranchiseResponseDTO>> addFranchise(
            @RequestBody
            @Parameter(description = "Franchise data for the new franchise", required = true)
            @Valid FranchiseRequestDTO franchiseRequestDTO) {
        log.info("Received request to create a new franchise");
        return franchiseService.createFranchise(franchiseRequestDTO)
                .map(createdFranchise -> {
                    log.info("Franchise created successfully with name: {}", franchiseRequestDTO.getName());
                    return ResponseEntity.status(HttpStatus.CREATED).body(createdFranchise);
                })
                .doOnError(error -> log.error("Failed to create franchise: {}", error.getMessage()));
    }

    @PutMapping("/{franchiseId}/name")
    @Operation(summary = "Update Franchise Name", description = "Updates the name of an existing franchise.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Franchise name successfully updated.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FranchiseResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Franchise not found.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Mono<ResponseEntity<FranchiseResponseDTO>> updateFranchiseName(
            @PathVariable @Parameter(description = "ID of the franchise to be updated", required = true, example = "1") Long franchiseId,
            @RequestBody @Parameter(description = "New name for the franchise", required = true) @Valid UpdateNameDTO updateNameDTO) {
        log.info("Received request to update franchise name for franchise ID: {}", franchiseId);
        return franchiseService.updateFranchiseName(franchiseId, updateNameDTO)
                .map(updatedFranchise -> {
                    log.info("Franchise name updated successfully for franchise ID: {}", franchiseId);
                    return ResponseEntity.status(HttpStatus.OK).body(updatedFranchise);
                })
                .doOnError(error -> log.error("Failed to update franchise name for franchise ID {}: {}", franchiseId, error.getMessage()));
    }
}
