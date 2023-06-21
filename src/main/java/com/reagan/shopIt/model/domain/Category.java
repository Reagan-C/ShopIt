package com.reagan.shopIt.model.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "category_name", nullable = false)
    private String name;

    @Column(name = "abbreviaton", nullable = false, unique = true)
    private String abbreviation;

    @OneToMany(mappedBy = "category")
    private Set<Products> products = new HashSet<>();
}

