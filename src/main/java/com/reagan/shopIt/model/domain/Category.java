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

    @Column(name = "abbreviation", nullable = false)
    private String abbreviation;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Item> items = new HashSet<>();

    //add items to category
    public void addItemToCategory(Item item) {
        this.getItems().add(item);
    }

    public void removeItemFromCategory() {
        this.setItems(new HashSet<>());
    }
}

