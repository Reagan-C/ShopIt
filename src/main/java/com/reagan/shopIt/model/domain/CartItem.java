package com.reagan.shopIt.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Item item;

    private int count = 1;

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
