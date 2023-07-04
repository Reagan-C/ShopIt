package com.reagan.shopIt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.reagan.shopIt.model.domain.UserRole;
import com.reagan.shopIt.model.dto.roledto.UpdateUserRoleDTO;
import com.reagan.shopIt.model.dto.roledto.UserRoleDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserRoleService {

    ResponseEntity<?> addNewRole(UserRoleDTO userRoleDTO);

    ResponseEntity<?> updateRole(UpdateUserRoleDTO updateUserRoleDTO);

    ResponseEntity<?> deleteRole(UserRoleDTO userRoleDTO);

    List<UserRole> getAllRoles();
}
