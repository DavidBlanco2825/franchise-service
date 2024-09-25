package com.example.franchiseservice.repository;

import com.example.franchiseservice.entity.Franchise;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FranchiseRepository extends ReactiveCrudRepository<Franchise, Long> {
}
