package com.reagan.shopIt.service.ServiceImpl;

import com.reagan.shopIt.model.domain.UserRole;
import com.reagan.shopIt.model.dto.roledto.UpdateUserRoleDTO;
import com.reagan.shopIt.model.dto.roledto.UserRoleDTO;
import com.reagan.shopIt.model.exception.UserRoleDuplicateEntityException;
import com.reagan.shopIt.model.exception.UserRoleNotFoundException;
import com.reagan.shopIt.repository.RoleRepository;
import com.reagan.shopIt.service.UserRoleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements UserRoleService {

    @Autowired
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<?> addNewRole(UserRoleDTO userRoleDTO) {
        Optional<UserRole> role = roleRepository.findByCode(userRoleDTO.getTitle());
        if (role.isPresent()) {
            throw new UserRoleDuplicateEntityException();
        }
        //create new role if null
        UserRole userRole = new UserRole();
        userRole.setCode(userRoleDTO.getCode());
        userRole.setTitle(userRoleDTO.getTitle());

        //save newly created role
        roleRepository.save(userRole);
        return ResponseEntity.ok("Role saved successfully");
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateRole(UpdateUserRoleDTO updateUserRoleDTO) {
        Optional<UserRole> userRole = roleRepository.findByTitle(updateUserRoleDTO.getOldTitle());
        if (userRole.isEmpty()) {
            throw new UserRoleNotFoundException(updateUserRoleDTO.getOldTitle());
        }
        userRole.get().setTitle(updateUserRoleDTO.getNewTitle());
        userRole.get().setCode(updateUserRoleDTO.getCode());
        userRole.get().setUpdatedOn(new Date());

        //save updated role
        roleRepository.save(userRole.get());
        return ResponseEntity.ok("Role updated successfully");
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteRole(UserRoleDTO userRoleDTO) {
        Optional<UserRole> roleExists = roleRepository.findByTitleAndCode(userRoleDTO.getTitle(),
                userRoleDTO.getCode());
        roleExists.ifPresent(roleRepository::delete);
        return ResponseEntity.status(HttpStatus.OK).body("Role deleted");
    }

    @Override
    public List<UserRole> getAllRoles() {
        return roleRepository.getAllRoles();
    }
}
