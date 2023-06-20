package com.reagan.shopIt.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class FulfilledOrders {
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
    Set<PendingOrder> fulfilledOrders = new HashSet<>();

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", updatable = false, nullable = false)
    private Date createdOn;

    void addFulfilledOrder (PendingOrder item) {
        this.fulfilledOrders.add(item);
        System.out.println("Order Fulfilled");
    }
}
