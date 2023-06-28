package com.reagan.shopIt.repository;

import com.reagan.shopIt.model.domain.AuthToken;
import com.reagan.shopIt.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<AuthToken, Long> {

    @Query(value = "select i from AuthToken i", nativeQuery = true)
    List<AuthToken> getAllTokens();

    Optional<AuthToken> findByToken(String token);

    Optional<AuthToken> findByUser(User user);
}
