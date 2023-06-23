package com.reagan.shopIt.model.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "admin_id")
    private Set<User> users = new HashSet<>();

    public String addAdmin(User newAdmin) {
        this.getUsers().add(newAdmin);
        return newAdmin.getEmailAddress() + " added to administrators";
    }
}
