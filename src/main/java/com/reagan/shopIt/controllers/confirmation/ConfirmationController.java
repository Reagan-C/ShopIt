package com.reagan.shopIt.controllers.confirmation;

import com.reagan.shopIt.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/confirm", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ConfirmationController {

    private final UserService userService;

    public ConfirmationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/confirm-sign-up")
    public ResponseEntity<String> confirmSignUp(@RequestParam("token") UUID token) {
        return userService.confirmSignUpToken(token);
    }

    @GetMapping("/confirm-order")
    public String confirmOrder(@RequestParam("token") String token) {
        return userService.confirmOrder(token);
    }
}
