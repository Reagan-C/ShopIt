package com.reagan.shopIt.model.domain;

import jakarta.persistence.*;
import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToOne
    private User user;

    @ManyToOne
    private Item item;

    private int quantity;

    private double unitCost;
    private double totalCost;

    public void setRoundedTotalCost(double cost) {
        this.totalCost = Math.round(cost * 100)/100.0;
    }
}
