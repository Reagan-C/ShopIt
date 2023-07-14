package com.reagan.shopIt.repository;


import com.reagan.shopIt.model.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Query(value = "select i.* from admin i order by id asc", nativeQuery = true)
    List<Admin> getAllAdmins();

    Boolean existsByUserEmail(String emailAddress);

    Admin findByUserEmail(String emailAddress);

}
