package com.example.franchiseservice.service.impl;

import com.example.franchiseservice.dto.UpdateNameDTO;
import com.example.franchiseservice.dto.franchise.FranchiseRequestDTO;
import com.example.franchiseservice.dto.franchise.FranchiseResponseDTO;
import com.example.franchiseservice.entity.Franchise;
import com.example.franchiseservice.mapper.FranchiseMapper;
import com.example.franchiseservice.repository.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FranchiseServiceImplTest {

    private FranchiseRepository franchiseRepository;
    private FranchiseServiceImpl franchiseService;

    private FranchiseRequestDTO franchiseRequestDTO;
    private Franchise franchise;
    private FranchiseResponseDTO franchiseResponseDTO;

    @BeforeEach
    void setUp() {
        franchiseRepository = mock(FranchiseRepository.class);
        franchiseService = new FranchiseServiceImpl(franchiseRepository);

        franchiseRequestDTO = new FranchiseRequestDTO();
        franchiseRequestDTO.setName("New Franchise");

        franchise = new Franchise();
        franchise.setId(1L);
        franchise.setName("New Franchise");

        franchiseResponseDTO = FranchiseMapper.toResponseDto(franchise);
    }

    @Test
    void testCreateFranchise_Success() {
        when(franchiseRepository.save(any(Franchise.class))).thenReturn(Mono.just(franchise));

        Mono<FranchiseResponseDTO> result = franchiseService.createFranchise(franchiseRequestDTO);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getName().equals("New Franchise"))
                .verifyComplete();

        verify(franchiseRepository).save(any(Franchise.class));
    }

    @Test
    void testUpdateFranchiseName_Success() {
        when(franchiseRepository.findById(anyLong())).thenReturn(Mono.just(franchise));
        when(franchiseRepository.save(any(Franchise.class))).thenReturn(Mono.just(franchise));

        UpdateNameDTO updateNameDTO = new UpdateNameDTO();
        updateNameDTO.setName("Updated Franchise");

        Mono<FranchiseResponseDTO> result = franchiseService.updateFranchiseName(1L, updateNameDTO);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getName().equals("Updated Franchise"))
                .verifyComplete();

        verify(franchiseRepository).findById(anyLong());
        verify(franchiseRepository).save(any(Franchise.class));
    }
}
