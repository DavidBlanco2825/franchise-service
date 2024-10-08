package com.example.franchiseservice.repository;

import com.example.franchiseservice.entity.Branch;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface BranchRepository extends ReactiveCrudRepository<Branch, Long> {
    Flux<Branch> findByFranchiseId(Long franchiseId);
}
