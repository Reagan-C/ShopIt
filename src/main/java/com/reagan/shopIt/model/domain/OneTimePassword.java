package com.reagan.shopIt.model.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "one_time_password")
public class OneTimePassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "otp", nullable = false)
    private String otp;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "PasswordResetOTP{" +
                "id='" + id + '\'' +
                ", otp='" + otp + '\'' +
                ", user=" + user.getEmailAddress() +
                '}';
    }
}
