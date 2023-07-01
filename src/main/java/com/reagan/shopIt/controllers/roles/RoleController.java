package com.reagan.shopIt.controllers.roles;

import com.reagan.shopIt.model.domain.UserRole;
import com.reagan.shopIt.model.dto.roledto.UpdateUserRoleDTO;
import com.reagan.shopIt.model.dto.roledto.UserRoleDTO;
import com.reagan.shopIt.service.UserRoleService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "roles", produces = {MediaType.APPLICATION_JSON_VALUE},
                consumes = {MediaType.APPLICATION_JSON_VALUE})
@PreAuthorize("hasRole('Administrator')")
public class RoleController {

    private final UserRoleService roleService;

    public RoleController(UserRoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/add")
    ResponseEntity<?> addNewRole(@Validated @RequestBody UserRoleDTO roleDTO) {
        return roleService.addNewRole(roleDTO);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> deleteRole(@Validated @RequestBody UserRoleDTO roleDTO) {
        return roleService.deleteRole(roleDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRoleDetails(@Validated @RequestBody UpdateUserRoleDTO roleDTO) {
        return roleService.updateRole(roleDTO);
    }

    @GetMapping("/get-all")
    public List<UserRole> getAllRoles() {
        return roleService.getAllRoles();
    }
}
