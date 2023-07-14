package com.reagan.shopIt.repository;

import com.reagan.shopIt.model.domain.Country;
import com.reagan.shopIt.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmailAddress(String email);

    Boolean existsByEmailAddress(String emailAddress);

    boolean existsById(Long id);


    @Modifying
    @Query(value = "update User u set u.first_name = ?1, u.last_name = ?2, u.address = ?3, u.city = ?4, u.state = ?5, "+
            "u.country_id=?6, u.phone_number = ?7, u.updated_on = ?8 where u.id = ?9", nativeQuery = true)
    void updateUser(String firstName, String lastName, String address, String city, String state, Long country_id,
                    String phoneNumber, Date date, Long id);

    @Query(value = "select * from user  ORDER BY id ASC", nativeQuery = true)
    List<User> getAllUsers();

    @Query(value = "select u.id, u.first_name, u.last_name, u.email from user u where u.country_id = :id order by " +
            "u.id asc", nativeQuery = true)
    List<Object[]> findUserByCountryId(@Param("id") Long country_id);
}

