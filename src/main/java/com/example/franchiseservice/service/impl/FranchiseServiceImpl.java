package com.example.franchiseservice.service.impl;

import com.example.franchiseservice.dto.UpdateNameDTO;
import com.example.franchiseservice.dto.franchise.FranchiseRequestDTO;
import com.example.franchiseservice.dto.franchise.FranchiseResponseDTO;
import com.example.franchiseservice.entity.Franchise;
import com.example.franchiseservice.exception.ResourceNotFoundException;
import com.example.franchiseservice.mapper.FranchiseMapper;
import com.example.franchiseservice.repository.FranchiseRepository;
import com.example.franchiseservice.service.FranchiseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.example.franchiseservice.commons.Constants.FRANCHISE_NOT_FOUND_WITH_ID;

@Slf4j
@Service
@AllArgsConstructor
public class FranchiseServiceImpl implements FranchiseService {

    private final FranchiseRepository franchiseRepository;

    // Add a new franchise
    @Override
    public Mono<FranchiseResponseDTO> createFranchise(FranchiseRequestDTO franchiseRequestDTO) {
        log.info("Creating franchise with name: {}", franchiseRequestDTO.getName());
        Franchise franchise = FranchiseMapper.toEntity(franchiseRequestDTO);
        return franchiseRepository.save(franchise)
                .map(FranchiseMapper::toResponseDto)
                .doOnSuccess(franchiseResponseDTO -> log.info("Franchise created successfully with ID: {}", franchiseResponseDTO.getId()))
                .doOnError(error -> log.error("Error creating franchise: {}", error.getMessage()));
    }

    // Update the name of a franchise
    @Override
    public Mono<FranchiseResponseDTO> updateFranchiseName(Long franchiseId, UpdateNameDTO updateNameDTO) {
        log.info("Updating franchise name for franchise ID: {}", franchiseId);
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(FRANCHISE_NOT_FOUND_WITH_ID + franchiseId)))
                .flatMap(franchise -> {
                    franchise.setName(updateNameDTO.getName());
                    return franchiseRepository.save(franchise);
                })
                .map(FranchiseMapper::toResponseDto)
                .doOnSuccess(updatedFranchise -> log.info("Franchise name updated successfully for franchise ID: {}", franchiseId))
                .doOnError(error -> log.error("Error updating franchise name for franchise ID {}: {}", franchiseId, error.getMessage()));
    }
}
