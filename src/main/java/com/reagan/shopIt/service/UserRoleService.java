package com.reagan.shopIt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.reagan.shopIt.model.domain.UserRole;
import com.reagan.shopIt.model.dto.roledto.UpdateUserRoleDTO;
import com.reagan.shopIt.model.dto.roledto.UserRoleDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserRoleService {

    @Transactional
    ResponseEntity<?> addNewRole(UserRoleDTO userRoleDTO);

    @Modifying
    @Transactional
    ResponseEntity<?> updateRole(Long id, UpdateUserRoleDTO updateUserRoleDTO);

    ResponseEntity<?> deleteRole(Long id);

    List<UserRole> getAllRoles();
}
