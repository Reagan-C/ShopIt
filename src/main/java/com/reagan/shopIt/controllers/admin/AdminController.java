package com.reagan.shopIt.controllers.admin;

import com.reagan.shopIt.model.domain.User;
import com.reagan.shopIt.model.dto.admindto.AdminDTO;
import com.reagan.shopIt.service.AdminService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin", produces = {MediaType.APPLICATION_JSON_VALUE},
                consumes = {MediaType.APPLICATION_JSON_VALUE})
@PreAuthorize("hasRole('Administrator')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUserToAdmin(@Validated @RequestBody AdminDTO adminDTO) {
           return adminService.addUserToAdmin(adminDTO);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeAdmin(@Validated @RequestBody AdminDTO adminDTO) {
        return adminService.removeUserFromAdmin(adminDTO);
    }

    @GetMapping("/get-all")
    public List<?> getAllAdmin() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/find-admin")
    public User findAdmin(@Validated @RequestBody AdminDTO adminDTO) {
        return adminService.findUser(adminDTO);
    }

}
