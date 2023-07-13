package com.reagan.shopIt.controllers.user;

import com.reagan.shopIt.model.dto.cartdto.AddCartItemsDTO;
import com.reagan.shopIt.model.dto.emailaddressdto.EmailAddressDTO;
import com.reagan.shopIt.model.dto.userdto.UpdateUserDTO;
import com.reagan.shopIt.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "user", produces = {MediaType.APPLICATION_JSON_VALUE},
                consumes = {MediaType.APPLICATION_JSON_VALUE})
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUserDetails(@RequestParam("id") Long id, @Validated @RequestBody UpdateUserDTO body) {
        return userService.updateUser(id, body);
    }

    @PreAuthorize("hasRole('Administrator')")
    @GetMapping("/find-user-by-email-address")
    public Map<String, Object> findUserByEmail(@RequestBody @Validated EmailAddressDTO email) {
        return userService.findUserByEmail(email);
    }

    @PreAuthorize("hasRole('Administrator')")
    @GetMapping("/get-all-users")
    public List<Map<String, Object>> getAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping("/add-to-cart")
    public String addItemToCart(@Validated @RequestBody AddCartItemsDTO body) {
        userService.addItemToCart(body);
        return  "item added to cart";
    }

    @DeleteMapping("/remove-from-cart")
    public String removeItemFromCart(@RequestParam("user_id") Long userId, @RequestParam("item_id") Long itemId) {
        return userService.removeItemFromCart(userId, itemId);
    }

    @PostMapping("/place-order")
    public ResponseEntity<String> placeOrder(@RequestParam("id") Long id) {
        return userService.placeOrder(id);
    }

    @PostMapping("/send-order-confirmation-mail")
    public ResponseEntity<?> sendOrderConfirmationMail(@Validated @RequestBody EmailAddressDTO body) {
        return userService.confirmOrderReceptionMail(body);
    }

    @PostMapping("/confirm-order")
    public String confirmOrder(@RequestParam("token") String token) {
        return userService.confirmOrder(token);
    }

    @GetMapping("/get-cart")
    public List<Map<String, Object>> getAllItemsInCArt(@RequestParam("id") Long userId) {
        return userService.viewItemsInCart(userId);
    }

    @PreAuthorize("hasRole('Administrator')")
    @DeleteMapping("/delete-user-account")
    ResponseEntity<?> deleteUserAccount(@Validated @RequestBody EmailAddressDTO emailAddressDTO) {
        return userService.removeUser(emailAddressDTO);
    }

}

