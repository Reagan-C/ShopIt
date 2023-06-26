package com.reagan.shopIt.service;

import com.reagan.shopIt.model.dto.onetimepassword.OneTimePasswordDTO;
import com.reagan.shopIt.model.dto.userdto.SignUpDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<String> register(SignUpDTO body);

    ResponseEntity<String> confirmToken(OneTimePasswordDTO token);

}
