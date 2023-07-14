package com.reagan.shopIt.repository;

import com.reagan.shopIt.model.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByTitleAndCode(String title, String code);

    Optional<UserRole> findByTitle(String code);

    Optional<UserRole> findByCode(String code);

}
