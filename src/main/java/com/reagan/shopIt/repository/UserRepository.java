package com.reagan.shopIt.repository;

import com.reagan.shopIt.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

    User findByEmailAddress(String email);

    @Query(value = "select i from User i", nativeQuery = true)
    List<User> getAllUsers();
}

