package com.reagan.shopIt.controllers.admin;

import com.reagan.shopIt.model.domain.User;
import com.reagan.shopIt.model.dto.emailaddressdto.EmailAddressDTO;
import com.reagan.shopIt.service.AdminService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin", produces = {MediaType.APPLICATION_JSON_VALUE})
@PreAuthorize("hasRole('Administrator')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUserToAdmin(@Validated @RequestBody EmailAddressDTO email) {
           return adminService.addUserToAdmin(email);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeAdmin(@Validated @RequestBody EmailAddressDTO email) {
        return adminService.removeUserFromAdmin(email);
    }

    @GetMapping("/get-all")
    public List<?> getAllAdmin() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/find-admin")
    public List<User> findAdmin(@Validated @RequestBody EmailAddressDTO email) {
        return adminService.findUser(email);
    }

}
