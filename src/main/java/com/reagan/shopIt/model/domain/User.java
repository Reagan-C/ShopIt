package com.reagan.shopIt.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username = this.getEmailAddress();

    @Column(name = "email")
    private String emailAddress;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "enabled")
    private Boolean enabled = false;

    @Column(name ="city")
    private String city;

    @Temporal(TemporalType.DATE)
    @Column(name ="date_of_birth")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth = new Date();

    @Column(name ="state")
    private String state;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PendingOrder> pendingOrders = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FulfilledOrders> fulfilledOrders = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "roles_id", referencedColumnName = "id")
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

    public void addRegularRole(UserRole userRole) {
        this.getRoles().add(userRole);
    }

    public void addAdminRole(UserRole userRole) {
        this.getRoles().add(userRole);
    }

    public void removeFromAdmin(UserRole userRole) {
        this.getRoles().remove(userRole);
    }


    public void addPendingOrder(PendingOrder pendingOrder) {
        this.getPendingOrders().add(pendingOrder);
    }

    public void addFulfilledOrder(FulfilledOrders fulfilledOrder) {
        this.getFulfilledOrders().add(fulfilledOrder);
    }

    public void removeFromPendingOrder(PendingOrder pendingOrder) {
        this.getPendingOrders().remove(pendingOrder);
    }

    public void resetUserCart() {
        this.cart = new Cart();
    }

}

