package com.example.franchiseservice.service;

import com.example.franchiseservice.dto.UpdateNameDTO;
import com.example.franchiseservice.dto.branch.BranchRequestDTO;
import com.example.franchiseservice.dto.branch.BranchResponseDTO;
import reactor.core.publisher.Mono;

public interface BranchService {

    Mono<BranchResponseDTO> addBranchToFranchise(Long franchiseId, BranchRequestDTO branchRequestDTO);

    Mono<BranchResponseDTO> updateBranchName(Long branchId, UpdateNameDTO updateNameDTO);
}
