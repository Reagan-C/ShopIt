package com.reagan.shopIt.controllers.user;//package com.reagan.shopIt.controllers;

import com.reagan.shopIt.model.domain.Cart;
import com.reagan.shopIt.model.domain.User;
import com.reagan.shopIt.model.dto.cartdto.AddCartItemsDTO;
import com.reagan.shopIt.model.dto.cartdto.OrderCartItemsDTO;
import com.reagan.shopIt.model.dto.emailaddressdto.EmailAddressDTO;
import com.reagan.shopIt.model.dto.itemdto.AddItemDTO;
import com.reagan.shopIt.model.dto.userdto.UpdateUserDTO;
import com.reagan.shopIt.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public String removeItemFromCart(@Validated @RequestBody OrderCartItemsDTO body) {
        userService.removeItemFromCart(body);
        return body.getName() + " removed from cart";
    }

    @PostMapping("/place-order")
    public ResponseEntity<String> placeOrder(@Validated @RequestBody EmailAddressDTO email) {
        return userService.placeOrder(email);
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
    public Set<Cart> getAllItemsInCArt(@Validated @RequestBody EmailAddressDTO emailAddress) {
        return userService.viewItemsInCart(emailAddress);
    }

    @PreAuthorize("hasRole('Administrator')")
    @DeleteMapping("/delete-user-account")
    ResponseEntity<?> deleteUserAccount(@Validated @RequestBody EmailAddressDTO emailAddressDTO) {
        return userService.removeUser(emailAddressDTO);
    }

}

