package com.reagan.shopIt.model.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @ManyToOne
    private Item item = new Item();

    @ManyToOne
    private  Cart cart;

    @Column(name = "count_of_items", nullable = false)
    private int count = 1;

    @Column(name = "cart_item_price", nullable = false)
    private double cartItemPrice = this.getItem().getPrice();

    public void increaseCount() {
        count++;
    }

    public  void  reduceCount() {
        count--;
    }

    public void setNewPrice() {
        this.setCartItemPrice(this.cartItemPrice * count);
    }

    public void setItemNewQuantity() {
        this.getItem().setQuantity(this.getItem().getQuantity() - count);
    }
}
