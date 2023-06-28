package com.reagan.shopIt.service.ServiceImpl;

import com.reagan.shopIt.model.domain.Admin;
import com.reagan.shopIt.model.domain.User;
import com.reagan.shopIt.model.dto.admindto.AdminDTO;
import com.reagan.shopIt.model.enums.UserRoleType;
import com.reagan.shopIt.model.exception.UserNameNotFoundException;
import com.reagan.shopIt.repository.AdminRepository;
import com.reagan.shopIt.repository.RoleRepository;
import com.reagan.shopIt.repository.UserRepository;
import com.reagan.shopIt.service.AdminService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final AdminRepository adminRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository, AdminRepository adminRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<?> addUserToAdmin(AdminDTO adminDTO) {
        //check if user exists by email address
        User user = userRepository.findByUsername(adminDTO.getUserEmailAddress());
        if (user == null) {
            throw new UserNameNotFoundException(adminDTO.getUserEmailAddress());
        }

        boolean isUserAnAdmin = adminRepository.existsByUserEmail(adminDTO.getUserEmailAddress());
        if (isUserAnAdmin) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("User is already an admin");
        }
        //Create admin if user is not an admin already
        Admin admin = new Admin();
        admin.setUserId(user.getId());
        admin.setUserEmail(user.getEmailAddress());
        admin.setUserFirstName(user.getFirstName());
        admin.setUserLastName(user.getLastName());

        //assign admin role to user
        user.addAdminRole(roleRepository.findByTitle(UserRoleType.ADMINISTRATOR.getValue()));

        //save and persist admin and user
        userRepository.save(user);
        adminRepository.save(admin);

        return ResponseEntity.status(HttpStatus.CREATED).body(user.getFirstName() + " " + user.getLastName()
        + " has been made an admin");
    }

    @Override
    @Transactional
    public ResponseEntity<?> removeUserFromAdmin(AdminDTO adminDTO) {
        //null check on user
        User user = userRepository.findByEmailAddress(adminDTO.getUserEmailAddress());
        if (user == null) {
            throw new UserNameNotFoundException(adminDTO.getUserEmailAddress());
        }
        //check if user is an admin
        Admin admin = adminRepository.findByUserEmail(user.getEmailAddress());
        if (admin == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with email address " + user.getEmailAddress()
                    + " is not an admin");
        }
        //Remove from admins
        adminRepository.delete(admin);
        user.removeFromAdmin(roleRepository.findByTitle(UserRoleType.ADMINISTRATOR.getValue()));

        //Save user
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("User removed as admin");
        }

    @Override
    public List<?> getAllAdmins() {
        return adminRepository.findAll();
    }
}
