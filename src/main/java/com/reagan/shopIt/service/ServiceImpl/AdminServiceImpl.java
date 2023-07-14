package com.reagan.shopIt.service.ServiceImpl;

import com.reagan.shopIt.model.domain.Admin;
import com.reagan.shopIt.model.domain.User;
import com.reagan.shopIt.model.domain.UserRole;
import com.reagan.shopIt.model.dto.emailaddressdto.EmailAddressDTO;
import com.reagan.shopIt.model.enums.UserRoleType;
import com.reagan.shopIt.model.exception.UserNameNotFoundException;
import com.reagan.shopIt.repository.AdminRepository;
import com.reagan.shopIt.repository.RoleRepository;
import com.reagan.shopIt.repository.UserRepository;
import com.reagan.shopIt.service.AdminService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
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
    @Modifying
    public ResponseEntity<?> addUserToAdmin(EmailAddressDTO emailAddressDTO) {
        //check if user exists by email address
        User user = userRepository.findByEmailAddress(emailAddressDTO.getEmailAddress());
        if (user == null) {
            throw new UserNameNotFoundException(emailAddressDTO.getEmailAddress());
        }

        boolean isUserAnAdmin = adminRepository.existsByUserEmail(emailAddressDTO.getEmailAddress());
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
    @Modifying
    public ResponseEntity<?> removeUserFromAdmin(EmailAddressDTO body) {
        //null check on user
        User user = userRepository.findByEmailAddress(body.getEmailAddress());
        if (user == null) {
            throw new UserNameNotFoundException(body.getEmailAddress());
        }
        //check if user is an admin
        Admin admin = adminRepository.findByUserEmail(body.getEmailAddress());
        if (admin == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is not an admin");
        }

        Optional<UserRole> role = roleRepository.findByTitle(UserRoleType.ADMINISTRATOR.getValue());
        role.ifPresent(user::removeFromAdmin);

        //Remove from admins
        adminRepository.delete(admin);
        //Save user
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("User has been removed as admin");
        }

    @Override
    public List<Admin> getAllAdmins() {
        List<Admin> adminList = adminRepository.getAllAdmins();

        if (adminList.isEmpty()) {
            throw new IllegalArgumentException("No user has been made an admin yet");
        }
        return adminList;
    }

    @Override
    public List<User> findUser(EmailAddressDTO body) {
        List<User> userDetailsList = new ArrayList<>();

        boolean userExists = userRepository.existsByEmailAddress(body.getEmailAddress());
        if (!userExists) {
            throw new UserNameNotFoundException(body.getEmailAddress());
        }

        boolean isUserAnAdmin = adminRepository.existsByUserEmail(body.getEmailAddress());
        if (!isUserAnAdmin) {
            throw new IllegalArgumentException("User is not an admin");
        }

        User user = userRepository.findByEmailAddress(body.getEmailAddress());
        userDetailsList.add(user);
        return userDetailsList;
    }
}
