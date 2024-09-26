package com.example.franchiseservice.service.impl;

import com.example.franchiseservice.dto.UpdateNameDTO;
import com.example.franchiseservice.dto.branch.BranchRequestDTO;
import com.example.franchiseservice.dto.branch.BranchResponseDTO;
import com.example.franchiseservice.entity.Branch;
import com.example.franchiseservice.entity.Franchise;
import com.example.franchiseservice.exception.ResourceNotFoundException;
import com.example.franchiseservice.mapper.BranchMapper;
import com.example.franchiseservice.repository.BranchRepository;
import com.example.franchiseservice.repository.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BranchServiceImplTest {

    private BranchRepository branchRepository;
    private FranchiseRepository franchiseRepository;
    private BranchServiceImpl branchService;

    private BranchRequestDTO branchRequestDTO;
    private BranchResponseDTO branchResponseDTO;
    private Branch branch;

    @BeforeEach
    void setUp() {
        branchRepository = mock(BranchRepository.class);
        franchiseRepository = mock(FranchiseRepository.class);
        branchService = new BranchServiceImpl(franchiseRepository, branchRepository);

        branchRequestDTO = new BranchRequestDTO();
        branchRequestDTO.setName("New Branch");

        branch = new Branch();
        branch.setId(1L);
        branch.setFranchiseId(1L);
        branch.setName("New Branch");

        branchResponseDTO = BranchMapper.toResponseDto(branch);
    }

    @Test
    void testAddBranchToFranchise_Success() {
        Franchise franchise = new Franchise();
        franchise.setId(1L);

        when(franchiseRepository.findById(anyLong())).thenReturn(Mono.just(franchise));
        when(branchRepository.save(any(Branch.class))).thenReturn(Mono.just(branch));

        Mono<BranchResponseDTO> result = branchService.addBranchToFranchise(1L, branchRequestDTO);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getName().equals("New Branch") && response.getId().equals(1L))
                .verifyComplete();

        verify(franchiseRepository).findById(anyLong());
        verify(branchRepository).save(any(Branch.class));
    }


    @Test
    void testAddBranchToFranchise_FranchiseNotFound() {
        when(franchiseRepository.findById(anyLong())).thenReturn(Mono.empty());

        Mono<BranchResponseDTO> result = branchService.addBranchToFranchise(1L, branchRequestDTO);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ResourceNotFoundException && throwable.getMessage().contains("Franchise not found"))
                .verify();

        verify(franchiseRepository).findById(anyLong());
        verify(branchRepository, never()).save(any(Branch.class));
    }

    @Test
    void testUpdateBranchName_Success() {
        when(branchRepository.findById(anyLong())).thenReturn(Mono.just(branch));
        when(branchRepository.save(any(Branch.class))).thenReturn(Mono.just(branch));

        UpdateNameDTO updateNameDTO = new UpdateNameDTO();
        updateNameDTO.setName("Updated Branch");

        Mono<BranchResponseDTO> result = branchService.updateBranchName(1L, updateNameDTO);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getName().equals("Updated Branch"))
                .verifyComplete();

        verify(branchRepository).findById(anyLong());
        verify(branchRepository).save(any(Branch.class));
    }

    @Test
    void testUpdateBranchName_BranchNotFound() {
        when(branchRepository.findById(anyLong())).thenReturn(Mono.empty());

        UpdateNameDTO updateNameDTO = new UpdateNameDTO();
        updateNameDTO.setName("Updated Branch");

        Mono<BranchResponseDTO> result = branchService.updateBranchName(1L, updateNameDTO);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ResourceNotFoundException && throwable.getMessage().contains("Branch not found"))
                .verify();

        verify(branchRepository).findById(anyLong());
        verify(branchRepository, never()).save(any(Branch.class));
    }
}
