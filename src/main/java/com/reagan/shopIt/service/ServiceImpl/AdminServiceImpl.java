package com.reagan.shopIt.service.ServiceImpl;

import com.reagan.shopIt.model.domain.Admin;
import com.reagan.shopIt.model.domain.User;
import com.reagan.shopIt.model.domain.UserRole;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        User user = userRepository.findByEmailAddress(adminDTO.getUserEmailAddress());
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
        Optional<UserRole> roles = roleRepository.findByTitle(UserRoleType.ADMINISTRATOR.getValue());
        roles.ifPresent(user::addAdminRole);

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
        Optional<UserRole> role = roleRepository.findByTitle(UserRoleType.ADMINISTRATOR.getValue());
        role.ifPresent(user::removeFromAdmin);

        //Save user
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("User removed as admin");
        }

    @Override
    public List<String> getAllAdmins() {
        List<String> admins= new ArrayList<>();
        List<Admin> adminList = adminRepository.getAllAdmins();
        if (!adminList.isEmpty()) {
            for (Admin admin: adminList) {
                admins.add(admin.getUserEmail());
            }
            return admins;
        }
        admins.add("rrrrrrrt");
        return admins;
    }

    @Override
    public User findUser(AdminDTO adminDTO) {
        boolean userExists = userRepository.existsByEmailAddress(adminDTO.getUserEmailAddress());
        if (!userExists) {
            throw new UserNameNotFoundException(adminDTO.getUserEmailAddress());
        }

        boolean isUserAnAdmin = adminRepository.existsByUserEmail(adminDTO.getUserEmailAddress());
        if (!isUserAnAdmin) {
            throw new IllegalArgumentException("User is not an admin");
        }

        return userRepository.findByEmailAddress(adminDTO.getUserEmailAddress());
    }
}
