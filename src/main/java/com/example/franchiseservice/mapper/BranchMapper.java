package com.example.franchiseservice.mapper;

import com.example.franchiseservice.dto.branch.BranchRequestDTO;
import com.example.franchiseservice.dto.branch.BranchResponseDTO;
import com.example.franchiseservice.entity.Branch;

public class BranchMapper {

    public static BranchResponseDTO toResponseDto(Branch branch) {

        if (branch == null) {
            return null;
        }

        return new BranchResponseDTO(
                branch.getId(),
                branch.getName(),
                branch.getFranchiseId()
        );
    }

    public static Branch toEntity(BranchRequestDTO branchRequestDTO) {

        if (branchRequestDTO == null) {
            return null;
        }

        Branch branch = new Branch();
        branch.setName(branchRequestDTO.getName());

        return branch;
    }
}
