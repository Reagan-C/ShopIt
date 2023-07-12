package com.reagan.shopIt.service;

import com.reagan.shopIt.model.domain.Cart;
import com.reagan.shopIt.model.dto.cartdto.AddCartItemsDTO;
import com.reagan.shopIt.model.dto.cartdto.OrderCartItemsDTO;
import com.reagan.shopIt.model.dto.emailaddressdto.EmailAddressDTO;
import com.reagan.shopIt.model.dto.userdto.*;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface UserService {

    @Transactional
    ResponseEntity<String> register(SignUpDTO body);

    @Transactional
    ResponseEntity<String> confirmSignUpToken(UUID token);

    ResponseEntity<String> authenticate(SignInDTO body);

    ResponseEntity<?> signOut();

    String sendChangePasswordOtp(String email);

    @Transactional
    ResponseEntity<String> changePassword(ResetPasswordDTO resetPasswordDTO);

    @Transactional
    ResponseEntity<String> updateUser(Long id, UpdateUserDTO updateUserDTO);

    Map<String, Object> findUserByEmail(EmailAddressDTO email);

    List<Map<String, Object>> findAllUsers();

    @Transactional
    void addItemToCart(AddCartItemsDTO itemName);

    @Transactional
    void removeItemFromCart(OrderCartItemsDTO itemName);

    ResponseEntity<String> placeOrder(EmailAddressDTO emailAddressDTO);

    @Transactional
    ResponseEntity<?> confirmOrderReceptionMail(EmailAddressDTO emailAddressDTO);

    String confirmOrder (String token);

    Set<Cart> viewItemsInCart(EmailAddressDTO emailAddressDTO);

    List<String> getUserRoles(EmailAddressDTO dto);

    @Transactional
    ResponseEntity<?> removeUser(EmailAddressDTO emailAddressDTO);
}
