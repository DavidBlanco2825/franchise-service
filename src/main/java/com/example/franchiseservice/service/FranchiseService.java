package com.example.franchiseservice.service;

import com.example.franchiseservice.dto.UpdateNameDTO;
import com.example.franchiseservice.dto.franchise.FranchiseRequestDTO;
import com.example.franchiseservice.dto.franchise.FranchiseResponseDTO;
import reactor.core.publisher.Mono;

public interface FranchiseService {

    Mono<FranchiseResponseDTO> createFranchise(FranchiseRequestDTO franchiseRequestDTO);

    Mono<FranchiseResponseDTO> updateFranchiseName(Long franchiseId, UpdateNameDTO updateNameDTO);
}
