package com.reagan.shopIt.repository;

import com.reagan.shopIt.model.domain.OTP;
import com.reagan.shopIt.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {

    @Query(value = "select * from auth_token i", nativeQuery = true)
    List<OTP> getAllTokens();

    Optional<OTP> findByToken(String token);

    OTP findByUser(User user);

}
