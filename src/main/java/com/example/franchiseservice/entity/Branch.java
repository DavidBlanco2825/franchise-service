package com.example.franchiseservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("branch")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Branch {

    @Id
    private Long id;
    private String name;

    @Column("franchise_id")
    private Long franchiseId;
}
