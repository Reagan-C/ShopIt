package com.reagan.shopIt.model.domain;

import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class PendingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "pending_order_cart_items",
            joinColumns = @JoinColumn(
                    name = "pending_order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "cart_items_id", referencedColumnName = "id")
    )
    Set<CartItem> cartItems = new HashSet<>();

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", updatable = false, nullable = false)
    private Date createdOn;

    void addPendingOrder (CartItem item) {
        this.getCartItems().add(item);
    }

}

