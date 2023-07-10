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
    @Query(value = "update User u set u.first_name = ?1, u.last_name = ?2, u.address = ?3, u.city = ?4, u.state = ?5, "+
            "u.country_id=?6, u.phone_number = ?7 where u.id = ?8", nativeQuery = true)
    void updateUser(String firstName, String lastName, String address, String city, String state, Long country_id,
                    String phoneNumber, Long id);

    @Query(value = "select * from user  ORDER BY id ASC", nativeQuery = true)
    List<User> getAllUsers();
}

