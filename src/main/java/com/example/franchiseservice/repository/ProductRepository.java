package com.example.franchiseservice.repository;

import com.example.franchiseservice.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
    Flux<Product> findByBranchId(Long branchId);
}
