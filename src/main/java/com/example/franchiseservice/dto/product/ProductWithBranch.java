package com.example.franchiseservice.dto.product;

import com.example.franchiseservice.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithBranch {

    private Product product;
    private String branchName;
}
