package com.reagan.shopIt.service.ServiceImpl;

import com.reagan.shopIt.config.security.jwt.JwtTokenProvider;
import com.reagan.shopIt.model.domain.*;
import com.reagan.shopIt.model.dto.cartdto.AddCartItemsDTO;
import com.reagan.shopIt.model.dto.cartdto.OrderCartItemsDTO;
import com.reagan.shopIt.model.dto.emailaddressdto.EmailAddressDTO;
import com.reagan.shopIt.model.dto.userdto.*;
import com.reagan.shopIt.model.enums.UserRoleType;
import com.reagan.shopIt.model.exception.*;
import com.reagan.shopIt.repository.*;
import com.reagan.shopIt.service.EmailService;
import com.reagan.shopIt.service.UserService;
import com.reagan.shopIt.util.OTPGenerator;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private final FulfilledOrderRepository fulfilledOrderRepository;

    private final PendingOrderRepository pendingOrderRepository;

    private final OTPRepository otpRepository;

    private final RoleRepository roleRepository;

    private final CartRepository cartRepository;

    private final OTPGenerator generator;

    private final CountryRepository countryRepository;

    private final ItemRepository itemRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, FulfilledOrderRepository fulfilledOrderRepository,
                           PendingOrderRepository pendingOrderRepository, OTPRepository otpRepository,
                           RoleRepository roleRepository, CartRepository cartRepository, OTPGenerator generator, CountryRepository countryRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.fulfilledOrderRepository = fulfilledOrderRepository;
        this.pendingOrderRepository = pendingOrderRepository;
        this.otpRepository = otpRepository;
        this.roleRepository = roleRepository;
        this.cartRepository = cartRepository;
        this.generator = generator;
        this.countryRepository = countryRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<String> register(SignUpDTO body) {
        boolean existingUser = userRepository.existsByEmailAddress(body.getEmailAddress());
        if (existingUser) {
            throw new UserAlreadyExistsException();
        }

        Optional<UserRole> role = roleRepository.findByCode(UserRoleType.REGULAR.getValue());
        if (role.isEmpty()) {
            throw new UserRoleNotFoundException(UserRoleType.REGULAR.getValue());
        }

        // create new user if user does not exist, encode password and set role as regular
        User newUser = mapper.map(body, User.class);
        newUser.setPassword(passwordEncoder.encode(body.getPassword()));
        newUser.addRegularRole(role.get());

        //check if country exists and assign to user
        Country country = countryRepository.findByTitle(body.getCountry());
        if (country != null) {
            newUser.setCountry(country);
        } else {
            throw new IllegalArgumentException("Country doesn't exist by this title");
        }

        //Generate and save confirmation token to AuthToken class. also set user auth token to token
        String token = generator.createVerificationToken();
        AuthToken authToken = new AuthToken();
        authToken.setToken(token);
        authToken.setCreatedAt(LocalDateTime.now());
        authToken.setConfirmedAt(null);
        authToken.setExpiresAt(LocalDateTime.now().plusMinutes(20));
        authToken.setUser(newUser);

        newUser.setAuthenticationToken(token);

        // create link,  calling the confirm token api
        String link = "http://localhost:8080/api/v1/auth/confirm?token=" + token;

        // Send email notification and save user and auth token
//        emailService.sendAccountConfirmationMail(body.getEmailAddress(),
//                emailService.buildEmail(body.getFirstName(), link));
        userRepository.save(newUser);
        otpRepository.save(authToken);
        return ResponseEntity.ok("Account created, Please log into your Email to confirm your account "+ token);
    }

    @Override
    @Transactional
    public ResponseEntity<String> confirmSignUpToken(String token) {
        User user1;

        //check if token exists
        AuthToken confirmationToken = otpRepository.findByToken(token)
                .orElseThrow(InvalidOtpException::new);

        //check if token is already confirmed
        if (confirmationToken.getConfirmedAt() != null) {
            throw new ConfirmedOtpException();
        }

        //check if token has expired, and remove user if not enabled
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        user1 = userRepository.findByEmailAddress(confirmationToken.getUser().getEmailAddress());
        if (expiredAt.isBefore(LocalDateTime.now()) && user1.getEnabled().equals(false)) {
            userRepository.delete(user1);
            throw new ExpiredOtpException();
        }

        //if valid, set confirmed at. Also set user enabled to true and send welcome mail and clear token in user
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        user1.setEnabled(true);
        user1.setAuthenticationToken(null);
//        emailService.sendWelcomeMessage(user1.getEmailAddress());

        userRepository.save(user1);
        otpRepository.save(confirmationToken);

        return ResponseEntity.ok("Account has been confirmed, Please visit site to log in!!!");

    }

    @Override
    public ResponseEntity<String> authenticate(SignInDTO body) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(body.getEmailAddress(),
                    body.getPassword()));
        } catch (Exception e) {
            throw new IllegalArgumentException("Username or Password incorrect");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(body.getEmailAddress());
        String jwt =  tokenProvider.generateJwtToken(body.getEmailAddress());
        return ResponseEntity.ok().body(jwt);
    }

    @Override
    public ResponseEntity<?> signOut() {
        return ResponseEntity.ok().body(
                "You have been signed out"
        );
    }

    @Override
    public String sendChangePasswordOtp(String email) {
        User user = userRepository.findByEmailAddress(email);
        if (user == null) {
            throw new UserNameNotFoundException(email);
        }
        //Generate otp and send to user
        String token = generator.createPasswordResetToken();
        AuthToken authToken = new AuthToken();
        authToken.setToken(token);
        authToken.setCreatedAt(LocalDateTime.now());
        authToken.setExpiresAt(LocalDateTime.now().plusMinutes(20));
        authToken.setUser(user);
        authToken.setConfirmedAt(null);

        otpRepository.save(authToken);
        emailService.sendChangePasswordMail(email, token);

        return "Please check your Email for otp";
    }

    @Override
    @Transactional
    public ResponseEntity<String> changePassword(ResetPasswordDTO resetPasswordDTO) {

        User existingUser = userRepository.findByEmailAddress(resetPasswordDTO.getEmailAddress());
        //check if user exists
        if (existingUser == null) {
            throw new UserNameNotFoundException(resetPasswordDTO.getEmailAddress());
        }
        // check if new password is same as existing password
        if (existingUser.getPassword().equals(passwordEncoder.encode(resetPasswordDTO.getPassword()))) {
            throw new IllegalArgumentException("New password cannot be same with old password");
        }
        //check if token exists
        AuthToken confirmationToken = otpRepository.findByToken(resetPasswordDTO.getOtp())
                .orElseThrow(InvalidOtpException::new);

        //check if token is already confirmed
        if (confirmationToken.getConfirmedAt() != null) {
            throw new ConfirmedOtpException();
        }
        //check if token has expired
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new ExpiredOtpException();
        }
        //if token is valid, set confirmed at.
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        //set new existing user password
        existingUser.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));

        userRepository.save(existingUser);
        otpRepository.save(confirmationToken);
        //send email to user
        emailService.sendSuccessfulPasswordChangeMail(resetPasswordDTO.getEmailAddress());
        return ResponseEntity.ok("Password change successful");
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateUser(Long id, UpdateUserDTO updateUserDTO) {
       boolean userExists = userRepository.existsById(id);
       if (!userExists) {
           throw new UserNameNotFoundException(updateUserDTO.getFirstName()+ " " + updateUserDTO.getLastName());
       }

       userRepository.updateUser(updateUserDTO.getFirstName(), updateUserDTO.getLastName(),
               updateUserDTO.getAddress(), updateUserDTO.getCity(), updateUserDTO.getState(),
               updateUserDTO.getCountry(), updateUserDTO.getPhoneNumber(), id);
        return ResponseEntity.ok("Hello " + updateUserDTO.getFirstName() + ", Your update operation is successful");
    }

    @Override
    public ResponseEntity<User> findUserByEmail(String email) {
         User user = userRepository.findByEmailAddress(email);
         if (user == null) {
             throw new UserNameNotFoundException(email);
         }
         return ResponseEntity.ok(user);
    }

    @Override
    public List<User> findAllUsers() {
        List<User> allUsers = userRepository.getAllUsers();
        if (allUsers.isEmpty()) {
            throw new RuntimeException("No user saved yet");
        }
        return allUsers;
    }

    @Override
    @Transactional
    public void addItemToCart(AddCartItemsDTO addCartItemsDTO) {
        //first check if user exists
        User user = userRepository.findByEmailAddress(addCartItemsDTO.getEmailAddress());
        if (user == null) {
            throw new UserNameNotFoundException(addCartItemsDTO.getEmailAddress());
        }
        // then check if item exists by that name
        Item item = itemRepository.findByName(addCartItemsDTO.getItemName());
        if (item == null) {
            throw  new ItemNotFoundException(addCartItemsDTO.getItemName());
        }

        Cart cart = user.getCart();
        if (cart == null) {
            Cart newCart = new Cart();
            newCart.addItem(item);
        } else {
            cart.addItem(item);
        }
        user.setCart(cart);
        itemRepository.save(item);
        userRepository.save(user);

    }

    @Override
    @Transactional
    public void removeItemFromCart(OrderCartItemsDTO removeItemDTO) {
        //check if user is valid
        User newUser = userRepository.findByEmailAddress(removeItemDTO.getEmailAddress());
        if (newUser == null) {
            throw new UserNameNotFoundException(removeItemDTO.getEmailAddress());
        }
        //check if item exists with that name
        Item newItem = itemRepository.findByName(removeItemDTO.getName());
        if (newItem == null) {
            throw new ItemNotFoundException(removeItemDTO.getName());
        }
        // check if user cart exists
        Cart cart = newUser.getCart();
        if (cart == null) {
            throw new IllegalArgumentException("Cart is empty, Please add items to cart first");
        }
        cart.removeItem(newItem);
        newUser.setCart(cart);
        itemRepository.save(newItem);
        userRepository.save(newUser);
    }

    @Override
    public Set<CartItem> viewItemsInCart(EmailAddressDTO emailAddressDTO) {
        User user = userRepository.findByEmailAddress(emailAddressDTO.getEmailAddress());
        if (user == null) {
            throw new UserNameNotFoundException(emailAddressDTO.getEmailAddress());
        }
        Cart userCart = user.getCart();
        if (userCart == null) {
            throw new IllegalArgumentException("Cart is empty at the moment");
        }
        return userCart.getCartItems();
    }

    @Override
    @Transactional
    public ResponseEntity<String> placeOrder(EmailAddressDTO emailAddressDTO) {
        User user = userRepository.findByEmailAddress(emailAddressDTO.getEmailAddress());
        if (user == null) {
            throw new UserNameNotFoundException(emailAddressDTO.getEmailAddress());
        }
        Cart cartToOrder = user.getCart();
        if (cartToOrder == null) {
            throw new IllegalArgumentException("Cart is empty, Please select items first");
        }
        //Proceed to checkout

        //generate order tracking code
        String token = generator.createOrderTrackingCode();
        if (token == null) {
            throw new IllegalArgumentException("Problem encountered while generating token");
        }
        AuthToken authToken = new AuthToken();
        authToken.setUser(user);
        authToken.setCreatedAt(LocalDateTime.now());
        authToken.setConfirmedAt(null);
        authToken.setToken(token);
        authToken.setExpiresAt(LocalDateTime.now().plusDays(365));
        // save items in cart to pending orders table
        PendingOrder pendingOrder = new PendingOrder();
        pendingOrder.setUser(user);
        pendingOrder.setCart(cartToOrder);
        pendingOrder.setToken(token);
        pendingOrder.setCreatedOn(new Date());
        //update user field
        user.addPendingOrder(pendingOrder);
        user.setAuthenticationToken(token);

        // Get total price of items in cart
        double totalPrice = cartToOrder.getTotalPrice();

        //send order details email
        emailService.sendOrderTrackingMail(emailAddressDTO.getEmailAddress(),token);

        //reset fields and persist
        user.resetUserCart();
        cartToOrder.resetCart();
        pendingOrderRepository.save(pendingOrder);
        otpRepository.save(authToken);
        cartRepository.save(cartToOrder);
        userRepository.save(user);
        return ResponseEntity.ok("Order Placed. An order tracking code has been sent to your" +
                "registered email address. Total cost is " + totalPrice);
    }

    @Override
    @Transactional
    public ResponseEntity<?> confirmOrderReceptionMail(EmailAddressDTO emailAddressDTO) {
        User user = userRepository.findByEmailAddress(emailAddressDTO.getEmailAddress());
        if (user == null) {
            throw new UserNameNotFoundException(emailAddressDTO.getEmailAddress());
        }

        String token = user.getAuthenticationToken();
        if (token == null) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(user.getFirstName() + " " +
                    "has no valid token");
        }

        String link = "http://localhost:8080/api/v1/orders/confirm-order?token=" + token;

        FulfilledOrders fulfilledOrders = new FulfilledOrders();
        fulfilledOrders.setUser(user);
        fulfilledOrders.setCreatedOn(new Date());
        fulfilledOrders.setConfirmedOn(null);
        fulfilledOrders.setToken(token);

        PendingOrder pendingOrder = pendingOrderRepository.findByToken(token);
        pendingOrder.setConfirmed(true);
        fulfilledOrders.addFulfilledOrder(pendingOrder);

        // To remove order from user's pending order
        user.removeFromPendingOrder(pendingOrder);

        // save updates
        fulfilledOrderRepository.save(fulfilledOrders);
        pendingOrderRepository.save(pendingOrder);
        userRepository.save(user);

        emailService.sendOrderDeliveryMail(emailAddressDTO.getEmailAddress(),
                emailService.buildOrderConfirmationEmail(user.getFirstName(), link));

        return ResponseEntity.status(HttpStatus.OK).body("Please proceed to your email account to confirm");
    }

    @Override
    @Transactional
    public String confirmOrder(String token) {

        //check if token exists
        AuthToken confirmationToken = otpRepository.findByToken(token)
                .orElseThrow(InvalidOtpException::new);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new ConfirmedOtpException();
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new ExpiredOtpException();
        }

        FulfilledOrders fulfilledOrder = fulfilledOrderRepository.findByToken(token);
        if (fulfilledOrder == null) {
            throw new IllegalArgumentException("Fulfilled order associated with token not found");
        }

        User user = userRepository.findByAuthenticationToken(token);
        if (user == null) {
            throw new IllegalArgumentException("No user found for token");
        }

        fulfilledOrder.setConfirmedOn(new Date());
        confirmationToken.setConfirmedAt(LocalDateTime.now());

        user.addFulfilledOrder(fulfilledOrder);
        user.setAuthenticationToken(null);

        otpRepository.save(confirmationToken);
        fulfilledOrderRepository.save(fulfilledOrder);
        userRepository.save(user);

        return "Thank you for shopping with us!";
    }

    public List<String> getUserRoles(EmailAddressDTO dto) {
        User user1 = userRepository.findByEmailAddress(dto.getEmailAddress());
        Set<UserRole> roles = user1.getRoles();
        List<String> myRoles = new ArrayList<>();
        for (UserRole role: roles) {
           myRoles.add(role.getTitle());
        }
        return myRoles;
    }

}
