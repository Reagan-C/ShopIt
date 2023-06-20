package com.reagan.shopIt.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private double price;

    @ManyToMany
    @JoinTable(
            name = "cart_item_products",
            joinColumns = @JoinColumn(
                    name = "cart_item_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "products_id", referencedColumnName = "id")
    )
    private Set<Products> products = new HashSet<>();

    void getCart(Products product) {
        this.getProducts().add(product);
    }

}
