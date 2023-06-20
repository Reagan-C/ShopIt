package com.reagan.shopIt.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name ="city", nullable = false)
    private String city;

    @Temporal(TemporalType.DATE)
    @Column(name ="date_of_birth")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth = new Date();

    @Column(name ="state")
    private String state;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "nationality_id")
    private Country nationality;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_item_id")
    private Set<CartItem> cartItems = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_pending_orders",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "pending_order_id", referencedColumnName = "id")
    )
    private Set<PendingOrder> pendingOrders = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_fulfilled_orders",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "fulfilled_order_id", referencedColumnName = "id")
    )
    private Set<FulfilledOrders> fulfilledOrders = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_role_id", referencedColumnName = "id")
    )
    private Set<UserRole> roles = new HashSet<>();

    @Column(name = "authentication_token")
    private String authenticationToken = null;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", updatable = false, nullable = false)
    private Date createdOn;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on", nullable = false)
    private Date updatedOn;

    public void addRole(UserRole userRole) {
        this.getRoles().add(userRole);
    }

    public void addPendingOrder(PendingOrder pendingOrder) {
        this.getPendingOrders().add(pendingOrder);
    }

    public void addFulfilledOrder(FulfilledOrders fulfilledOrder) {
        this.getFulfilledOrders().add(fulfilledOrder);
    }

    public void addCartItem(CartItem cartItem) {
        this.getCartItems().add(cartItem);
    }
}

