package com.reagan.shopIt.repository;

import com.reagan.shopIt.model.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {

    @Query(value = "select i from UserRole i", nativeQuery = true)
    List<UserRole> getAllRoles();
}
