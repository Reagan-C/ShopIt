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
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Set<CartItem> cartItems = new HashSet<>();


    double totalPrice = 0.0;
    public String addItem(Item item) {
        if (item.getQuantity() > 0) {
            //To check if item is already present and increase its count and amount if true
            for (CartItem cartItem : cartItems) {
                if (cartItem.getItem().equals(item)) {
                    cartItem.increaseCount();
                    cartItem.setNewPrice();
                } else {
                    CartItem newCartItem = new CartItem();
                    newCartItem.setItem(item);
                    cartItems.add(newCartItem);
                }
                // set total price of all items in the cart
                totalPrice += cartItem.getCartItemPrice();
                cartItem.setItemNewQuantity();
            }
            return "Item added to cart";
        }
        return "Out of stock";
    }

    public String removeItem(Item item) {

        for (CartItem cartItem : cartItems) {
            if (cartItem.getItem().equals(item)) {
                cartItem.reduceCount();
                cartItem.setNewPrice();
                // set new quantity of item
                cartItem.setItemNewQuantity();
                totalPrice -= cartItem.getCartItemPrice();
                return "Item removed from cart";
            }
        }
        return "Item not found in cart";
    }

    public void resetCart() {
        this.cartItems = new HashSet<>();
    }
}
