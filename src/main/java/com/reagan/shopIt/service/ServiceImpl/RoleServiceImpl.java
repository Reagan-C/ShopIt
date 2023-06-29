package com.reagan.shopIt.service.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.reagan.shopIt.model.domain.UserRole;
import com.reagan.shopIt.model.dto.roledto.UpdateUserRoleDTO;
import com.reagan.shopIt.model.dto.roledto.UserRoleDTO;
import com.reagan.shopIt.model.exception.UserRoleDuplicateEntityException;
import com.reagan.shopIt.model.exception.UserRoleNotFoundException;
import com.reagan.shopIt.repository.RoleRepository;
import com.reagan.shopIt.service.UserRoleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

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
    @TransactionalEventListener(ApplicationReadyEvent.class)
    public void seedRoles() throws JsonProcessingException {
//        String userRolesAsString = readResourceFile("json/roles.json");
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        UserRole[] rolesArray = objectMapper.readValue(userRolesAsString, UserRole[].class);
//        List<UserRole> roleList = Arrays.asList(rolesArray);
//
//        try {
//            for (UserRole role : roleList) {
//                if (false) {
//                    Optional<UserRole> roleExists = roleRepository.findByTitleAndCode(role.getTitle(), role.getCode());
//
//                    if (roleExists.isPresent()) {
//                        continue;
//                    }
//
//                    role.setId(null);
//                    roleRepository.save(role);
//                }
//            }
//        }catch (UserRoleDuplicateEntityException e){
//
//        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> addNewRole(UserRoleDTO userRoleDTO) {
        UserRole role = roleRepository.findByTitle(userRoleDTO.getTitle());
        if (role != null) {
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
        UserRole userRole = roleRepository.findByTitle(updateUserRoleDTO.getOldTitle());
        if (userRole == null) {
            throw new UserRoleNotFoundException(updateUserRoleDTO.getOldTitle());
        }
        userRole.setTitle(updateUserRoleDTO.getNewTitle());
        userRole.setCode(updateUserRoleDTO.getCode());
        userRole.setUpdatedOn(new Date());

        //save updated role
        roleRepository.save(userRole);
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
