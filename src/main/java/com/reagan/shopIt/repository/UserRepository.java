package com.reagan.shopIt.repository;

import com.reagan.shopIt.model.domain.Country;
import com.reagan.shopIt.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

    User findByEmailAddress(String email);

    User findByAuthenticationToken(String token);

    Boolean existsByEmailAddress(String emailAddress);

    boolean existsById(Long id);

    @Modifying
    @Query(value = "update User u set u.firstName = ?1, u.lastName = ?2, u.address = ?3, u.city = ?4, u.country = ?5, "+
            "u.phoneNumber = ?6 where u.id = ?7", nativeQuery = true)
    User updateUser(String firstName, String lastName, String address, String city, String state, Country country,
                    String phoneNumber, Long id);

    @Query(value = "select u from User u order by u.id desc", nativeQuery = true)
    List<User> getAllUsers();
}

