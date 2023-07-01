package com.reagan.shopIt.controllers.authentication;

import com.reagan.shopIt.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "auth", produces = {MediaType.APPLICATION_JSON_VALUE},
                consumes = {MediaType.APPLICATION_JSON_VALUE})
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }


}
