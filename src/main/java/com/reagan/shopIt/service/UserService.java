package com.reagan.shopIt.service;

import com.reagan.shopIt.model.dto.cartdto.AddCartItemsDTO;
import com.reagan.shopIt.model.dto.emailaddressdto.EmailAddressDTO;
import com.reagan.shopIt.model.dto.userdto.*;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserService {

    @Transactional
    ResponseEntity<String> register(SignUpDTO body);

    @Transactional
    ResponseEntity<String> confirmSignUpToken(UUID token);

    @Transactional
    ResponseEntity<Map<String, Object>> authenticate(SignInDTO body);

    ResponseEntity<?> signOut();

    String sendChangePasswordOtp(EmailAddressDTO email);

    @Transactional
    ResponseEntity<String> changePassword(ResetPasswordDTO resetPasswordDTO);

    @Transactional
    ResponseEntity<String> updateUser(Long id, UpdateUserDTO updateUserDTO);

    Map<String, Object> findUserByEmail(EmailAddressDTO email);

    List<Map<String, Object>> findAllUsers();

    @Transactional
    void addItemToCart(AddCartItemsDTO itemName);

    @Transactional
    String removeItemFromCart(Long userId, Long itemId);

    ResponseEntity<String> placeOrder(Long userId);

    @Transactional
    ResponseEntity<?> confirmOrderReceptionMail(Long id, Long userId);

    String confirmOrder (String token);

    List<Map<String, Object>> viewItemsInCart(Long id);

    List<String> getUserRoles(EmailAddressDTO dto);

    @Transactional
    ResponseEntity<?> removeUser(EmailAddressDTO emailAddressDTO);
}
