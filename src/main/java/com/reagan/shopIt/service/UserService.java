package com.reagan.shopIt.service;

import com.reagan.shopIt.model.domain.CartItem;
import com.reagan.shopIt.model.domain.User;
import com.reagan.shopIt.model.dto.cartdto.AddCartItemsDTO;
import com.reagan.shopIt.model.dto.cartdto.OrderCartItemsDTO;
import com.reagan.shopIt.model.dto.emailaddressdto.EmailAddressDTO;
import com.reagan.shopIt.model.dto.userdto.*;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface UserService {

    @Transactional
    ResponseEntity<String> register(SignUpDTO body);

    @Transactional
    ResponseEntity<String> confirmSignUpToken(String token);

    ResponseEntity<String> authenticate(SignInDTO body);

    ResponseEntity<?> signOut();

    String sendChangePasswordOtp(String email);

    @Transactional
    ResponseEntity<String> changePassword(ResetPasswordDTO resetPasswordDTO);

    @Transactional
    ResponseEntity<String> updateUser(Long id, UpdateUserDTO updateUserDTO);

    ResponseEntity<User> findUserByEmail(String email);

    List<User> findAllUsers();

    @Transactional
    void addItemToCart(AddCartItemsDTO itemName);

    @Transactional
    void removeItemFromCart(OrderCartItemsDTO itemName);

    ResponseEntity<String> placeOrder(EmailAddressDTO emailAddressDTO);

    @Transactional
    ResponseEntity<?> confirmOrderReceptionMail(EmailAddressDTO emailAddressDTO);

    String confirmOrder (String token);

    Set<CartItem> viewItemsInCart(EmailAddressDTO emailAddressDTO);


}
