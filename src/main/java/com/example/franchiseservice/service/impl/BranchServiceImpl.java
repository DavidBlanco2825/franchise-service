package com.example.franchiseservice.service.impl;

import com.example.franchiseservice.dto.UpdateNameDTO;
import com.example.franchiseservice.dto.branch.BranchRequestDTO;
import com.example.franchiseservice.dto.branch.BranchResponseDTO;
import com.example.franchiseservice.entity.Branch;
import com.example.franchiseservice.exception.ResourceNotFoundException;
import com.example.franchiseservice.mapper.BranchMapper;
import com.example.franchiseservice.repository.BranchRepository;
import com.example.franchiseservice.repository.FranchiseRepository;
import com.example.franchiseservice.service.BranchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.example.franchiseservice.commons.Constants.BRANCH_NOT_FOUND_WITH_ID;
import static com.example.franchiseservice.commons.Constants.FRANCHISE_NOT_FOUND_WITH_ID;

@Slf4j
@Service
@AllArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final FranchiseRepository franchiseRepository;
    private final BranchRepository branchRepository;

    // Add a new branch to a franchise
    @Override
    public Mono<BranchResponseDTO> addBranchToFranchise(Long franchiseId, BranchRequestDTO branchRequestDTO) {
        log.info("Adding branch to franchise ID: {}", franchiseId);
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(FRANCHISE_NOT_FOUND_WITH_ID + franchiseId)))
                .flatMap(franchise -> {
                    Branch branch = BranchMapper.toEntity(branchRequestDTO);
                    branch.setFranchiseId(franchiseId);
                    return branchRepository.save(branch);
                })
                .map(BranchMapper::toResponseDto)
                .doOnSuccess(branch -> log.info("Branch added successfully to franchise ID: {}", franchiseId))
                .doOnError(error -> log.error("Error adding branch to franchise ID {}: {}", franchiseId, error.getMessage()));
    }

    // Update the name of a branch
    @Override
    public Mono<BranchResponseDTO> updateBranchName(Long branchId, UpdateNameDTO updateNameDTO) {
        log.info("Updating branch name for branch ID: {}", branchId);
        return branchRepository.findById(branchId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(BRANCH_NOT_FOUND_WITH_ID + branchId)))
                .flatMap(branch -> {
                    branch.setName(updateNameDTO.getName());
                    return branchRepository.save(branch);
                })
                .map(BranchMapper::toResponseDto)
                .doOnSuccess(updatedBranch -> log.info("Branch name updated successfully for branch ID: {}", branchId))
                .doOnError(error -> log.error("Error updating branch name for branch ID {}: {}", branchId, error.getMessage()));
    }
}