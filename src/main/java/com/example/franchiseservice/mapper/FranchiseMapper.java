package com.example.franchiseservice.mapper;

import com.example.franchiseservice.dto.franchise.FranchiseRequestDTO;
import com.example.franchiseservice.dto.franchise.FranchiseResponseDTO;
import com.example.franchiseservice.entity.Franchise;

public class FranchiseMapper {

    public static FranchiseResponseDTO toResponseDto(Franchise franchise) {

        if (franchise == null) {
            return null;
        }

        return new FranchiseResponseDTO(
                franchise.getId(),
                franchise.getName()
        );
    }

    public static Franchise toEntity(FranchiseRequestDTO franchiseRequestDTO) {

        if (franchiseRequestDTO == null) {
            return null;
        }

        Franchise franchise = new Franchise();
        franchise.setName(franchiseRequestDTO.getName());

        return franchise;
    }
}
