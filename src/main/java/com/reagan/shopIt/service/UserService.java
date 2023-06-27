package com.reagan.shopIt.service;

import com.reagan.shopIt.model.dto.onetimepassword.OneTimePasswordDTO;
import com.reagan.shopIt.model.dto.userdto.*;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

public interface UserService {

    @Transactional
    ResponseEntity<String> register(SignUpDTO body);

    @Transactional
    ResponseEntity<String> confirmSignUpToken(OneTimePasswordDTO token);

    ResponseEntity<String> authenticate(SignInDTO body);

    ResponseEntity<?> signOut();

    String sendChangePasswordOtp(ForgotPasswordDTO email);

    @Transactional
    ResponseEntity<String> changePassword(ResetPasswordDTO resetPasswordDTO);

    @Transactional
    ResponseEntity<String> updateUser(UpdateUserDTO updateUserDTO);
}
