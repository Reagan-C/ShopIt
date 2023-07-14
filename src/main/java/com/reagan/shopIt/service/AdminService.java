package com.reagan.shopIt.service;

import com.reagan.shopIt.model.domain.Admin;
import com.reagan.shopIt.model.domain.User;
import com.reagan.shopIt.model.dto.emailaddressdto.EmailAddressDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {

    @Transactional
    ResponseEntity<?> addUserToAdmin(EmailAddressDTO email);

    @Transactional
    ResponseEntity<?> removeUserFromAdmin(EmailAddressDTO email);

    List<Admin> getAllAdmins();

    List<User> findUser (EmailAddressDTO email);
}
