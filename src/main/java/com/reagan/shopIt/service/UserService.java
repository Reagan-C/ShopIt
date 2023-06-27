package com.reagan.shopIt.service;

import com.reagan.shopIt.model.domain.CartItem;
import com.reagan.shopIt.model.domain.User;
import com.reagan.shopIt.model.dto.cartdto.AddCartItemsDTO;
import com.reagan.shopIt.model.dto.cartdto.OrderCartItemsDTO;
import com.reagan.shopIt.model.dto.onetimepassword.OneTimePasswordDTO;
import com.reagan.shopIt.model.dto.userdto.*;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

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

    ResponseEntity<User> findUserByEmail(String emailAddress);

    List<User> findAllUsers();

    @Transactional
    void addItemToCart(AddCartItemsDTO itemName);

    @Transactional
    void RemoveItemFromCart(OrderCartItemsDTO itemName);

    ResponseEntity<String> placeOrder(String emailAddress);

    ResponseEntity<String> confirmOrderReceptionMail(EmailAddressDTO email);

    String confirmOrder (ConfirmOrderDTO orderDTO);

    Set<CartItem> viewItemsInCart(String emailAddress);


}
