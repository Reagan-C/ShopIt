package com.reagan.shopIt.service;

import com.reagan.shopIt.model.domain.User;
import com.reagan.shopIt.model.dto.admindto.AdminDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {

    @Transactional
    ResponseEntity<?> addUserToAdmin(AdminDTO adminDTO);

    @Transactional
    ResponseEntity<?> removeUserFromAdmin(AdminDTO adminDTO);

    List<?> getAllAdmins();

    User findUser (AdminDTO adminDTO);
}
