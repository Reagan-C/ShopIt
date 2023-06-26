package com.reagan.shopIt.service.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reagan.shopIt.model.domain.UserRole;
import com.reagan.shopIt.model.exception.UserRoleDuplicateEntityException;
import com.reagan.shopIt.repository.RoleRepository;
import com.reagan.shopIt.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.reagan.shopIt.util.ShopItUtil.readResourceFile;

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
        String userRolesAsString = readResourceFile("json/roles.json");
        ObjectMapper objectMapper = new ObjectMapper();

        UserRole[] rolesArray = objectMapper.readValue(userRolesAsString, UserRole[].class);
        List<UserRole> roleList = Arrays.asList(rolesArray);

        try {
            for (UserRole role : roleList) {
                if (false) {
                    Optional<UserRole> roleExists = roleRepository.findByTitleAndCode(role.getTitle(), role.getCode());

                    if (roleExists.isPresent()) {
                        continue;
                    }

                    role.setId(null);
                    roleRepository.save(role);
                }
            }
        }catch (UserRoleDuplicateEntityException e){

        }
    }
}
