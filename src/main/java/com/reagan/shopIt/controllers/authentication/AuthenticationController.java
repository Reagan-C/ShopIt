package com.reagan.shopIt.controllers.authentication;

import com.reagan.shopIt.model.dto.userdto.ResetPasswordDTO;
import com.reagan.shopIt.model.dto.userdto.SignInDTO;
import com.reagan.shopIt.model.dto.userdto.SignUpDTO;
import com.reagan.shopIt.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "auth", produces = {MediaType.APPLICATION_JSON_VALUE},
                consumes = {MediaType.APPLICATION_JSON_VALUE})
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> create(@Validated @RequestBody SignUpDTO body) {
        return userService.register(body);
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmSignUp(@RequestParam("token") String token) {
        return userService.confirmSignUpToken(token);
    }

    @PostMapping("/log-in")
    public ResponseEntity<String> signIn(@Validated @RequestBody SignInDTO body) {
        return userService.authenticate(body);
    }

    @PostMapping("/log-out")
    public ResponseEntity<?> signOut() {
        return userService.signOut();
    }

    @PostMapping("/generate-password-reset-otp")
    public String generatePasswordChangeOtp(@RequestParam("email") String email) {
        return userService.sendChangePasswordOtp(email);
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@Validated @RequestBody ResetPasswordDTO body) {
        return userService.changePassword(body);
    }
}
