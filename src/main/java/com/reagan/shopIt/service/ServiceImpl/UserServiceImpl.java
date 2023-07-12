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
import org.joda.time.Days;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        //check if country exists
        Country country = countryRepository.findByTitle(body.getCountry());
        if (country == null) {
            throw new CountryNotFoundException(body.getCountry());
        }
        //check if user already exists in record
        boolean existingUser = userRepository.existsByEmailAddress(body.getEmailAddress());
        if (existingUser) {
            throw new UserAlreadyExistsException();
        }
        // create new user if user does not exist,then encode password and set country
        User newUser = mapper.map(body, User.class);
        newUser.setUsername(body.getEmailAddress());
        newUser.setPassword(passwordEncoder.encode(body.getPassword()));
        newUser.setCountry(country);

        //Generate and save confirmation token to OTP class. also set user auth token to token
        String token = generator.createVerificationToken();
        OTP otp = new OTP();
        otp.setToken(token);
        otp.setCreatedAt(LocalDateTime.now());
        otp.setConfirmedAt(null);
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(30));
        otp.setUser(newUser);

        newUser.setAuthenticationToken(token);

        // create link,  calling the confirm token api
        String link = "http://localhost:8080/api/v1/auth/confirm?token=" + token;

        // Send email notification and save user and auth token
//        emailService.sendAccountConfirmationMail(body.getEmailAddress(),
//                emailService.buildEmail(body.getFirstName(), link));
        userRepository.save(newUser);
        otpRepository.save(otp);
        return ResponseEntity.ok("Account created, Please log into your Email to confirm your account ");
    }

    @Override
    @Transactional
    public ResponseEntity<String> confirmSignUpToken(UUID token) {
        User user1;
        //check if token exists and get user if true
        OTP confirmationToken = otpRepository.findByToken(token.toString())
                .orElseThrow(InvalidOtpException::new);

        user1 = confirmationToken.getUser();
        //check if token is already confirmed
        if (confirmationToken.getConfirmedAt() != null) {
            throw new ConfirmedOtpException();
        }
        //check if token has expired, and remove user if true
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            user1.setCountry(null);
            userRepository.delete(user1);
            return  ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Your OTP has expired");
        }

        //if valid, set confirmed at, assign role and set user enabled to true and send welcome mail
        Optional<UserRole> role = roleRepository.findByCode(UserRoleType.REGULAR.getValue());
        if (role.isEmpty()) {
            throw new UserRoleNotFoundException(UserRoleType.REGULAR.getValue());
        }
        user1.addRegularRole(role.get());
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
            System.out.println(e.getLocalizedMessage());
            throw new IllegalArgumentException(" Authentication Failure");
        }
        String jwt =  tokenProvider.generateJwtToken(body.getEmailAddress());
        System.out.println("successful");
        return ResponseEntity.ok().body("Login successful " + jwt);
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
        //check if user performed update in the last 7 days and throw exception if true
        Days daysDifference  = Days.days(user.getUpdatedOn().compareTo(new Date()));
        if (daysDifference.isLessThan(Days.days(7))) {
            throw new InsufficientDaysBeforeUpdateException();
        }
        //Generate otp and send to user
        String token = generator.createPasswordResetToken();
        OTP OTP = new OTP();
        OTP.setToken(token);
        OTP.setCreatedAt(LocalDateTime.now());
        OTP.setExpiresAt(LocalDateTime.now().plusMinutes(20));
        OTP.setUser(user);
        OTP.setConfirmedAt(null);

        otpRepository.save(OTP);
//        emailService.sendChangePasswordMail(email, token);
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
        //check if token exists
        OTP confirmationToken = otpRepository.findByToken(resetPasswordDTO.getOtp())
                .orElseThrow(InvalidOtpException::new);
        //check if token is already confirmed
        if (confirmationToken.getConfirmedAt() != null) {
            throw new ConfirmedPasswordChangeOtpException();
        }
        //check if token has expired
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new ExpiredOtpException();
        }
        // check if new password is same as existing password
        if (existingUser.getPassword().equals(passwordEncoder.encode(resetPasswordDTO.getPassword()))) {
            throw new IllegalArgumentException("New password cannot be same with old password");
        }
        //if token is valid, set confirmed at.
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        //set new user password
        existingUser.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));

        userRepository.save(existingUser);
        otpRepository.save(confirmationToken);
        //send email to user
//        emailService.sendSuccessfulPasswordChangeMail(resetPasswordDTO.getEmailAddress());
        return ResponseEntity.ok("Password change successful");
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateUser(Long id, UpdateUserDTO updateUserDTO) {
        //check if user exists by id
       boolean userExists = userRepository.existsById(id);
       if (!userExists) {
           throw new UserIDNotFoundException(id);
       }
        //check if country exists
        Country country = countryRepository.findByTitle(updateUserDTO.getCountry());
        if (country == null) {
            throw new CountryNotFoundException(updateUserDTO.getCountry());
        }

       userRepository.updateUser(updateUserDTO.getFirstName(), updateUserDTO.getLastName(),
               updateUserDTO.getAddress(), updateUserDTO.getCity(), updateUserDTO.getState(),
               country.getId(), updateUserDTO.getPhoneNumber(), id);
        return ResponseEntity.ok("Hello " + updateUserDTO.getFirstName() + ", Your update operation is successful");
    }

    @Override
    public Map<String, Object> findUserByEmail(EmailAddressDTO email) {
        Map<String, Object> userDetails = new LinkedHashMap<>();
         User user = userRepository.findByEmailAddress(email.getEmailAddress());
         if (user == null) {
             throw new UserNameNotFoundException(email.getEmailAddress());
         }
         userDetails.put("ID:", user.getId());
         userDetails.put("Username:", user.getUsername());
         userDetails.put("Firstname:", user.getFirstName());
         userDetails.put("Lastname:", user.getLastName());
         userDetails.put("Address:", user.getAddress());
         userDetails.put("Date of birth:", user.getDateOfBirth());
         userDetails.put("City", user.getCity());
         userDetails.put("State", user.getState());
         userDetails.put("Country", user.getCountry().getTitle());
         userDetails.put("Fulfilled Orders", user.getFulfilledOrders());
         userDetails.put("Created account on", user.getCreatedOn().toString());
         userDetails.put("Last updated account on", user.getUpdatedOn().toString());
         return userDetails;
    }

    @Override
    public List<Map<String, Object>> findAllUsers() {
        //create list for adding object of type map
        List<Map<String, Object>> finalListOfUsers = new ArrayList<>();
        //query database to get all users
        List<User> listOfUsers = userRepository.getAllUsers();
        if (listOfUsers.isEmpty()) {
            throw new RuntimeException("No user saved yet");
        }
        //Loop through the list of all users, and extract needed details into a map
        for (User user : listOfUsers) {
            Map<String, Object> userDetail = new LinkedHashMap<>();

            userDetail.put("ID:", user.getId());
            userDetail.put("Username:", user.getUsername());
            userDetail.put("Firstname:", user.getFirstName());
            userDetail.put("Lastname:", user.getLastName());
            userDetail.put("Address:", user.getAddress());
            userDetail.put("Date of birth:", user.getDateOfBirth());
            userDetail.put("City", user.getCity());
            userDetail.put("State", user.getState());
            userDetail.put("Country", user.getCountry().getTitle());
            userDetail.put("Fulfilled Orders", user.getFulfilledOrders());
            userDetail.put("Created account on", user.getCreatedOn().toString());
            userDetail.put("Last updated account on", user.getUpdatedOn().toString());

            // add the extracted details into the map to be returned
            finalListOfUsers.add(userDetail);
        }
        return finalListOfUsers;
    }

    @Override
    @Transactional
    public ResponseEntity<?> removeUser(EmailAddressDTO emailAddressDTO) {
        User user = userRepository.findByEmailAddress(emailAddressDTO.getEmailAddress());
        if (user == null) {
            throw new UserNameNotFoundException(emailAddressDTO.getEmailAddress());
        }
        user.removeUserRoles();
        user.setCountry(null);

        cartRepository.deleteByUserId(user.getId());
        userRepository.delete(user);
        return ResponseEntity.ok("User account successfully deleted");
    }

    @Override
    @Transactional
    public void addItemToCart(AddCartItemsDTO addCartItemsDTO) {
        //first check if user exists
        User user = userRepository.findByEmailAddress(addCartItemsDTO.getEmailAddress());
        if (user == null) {
            throw new UserNameNotFoundException(addCartItemsDTO.getEmailAddress());
        }
        // then check if item exists by that name and if we have item in stock
        Item item = itemRepository.findByName(addCartItemsDTO.getItemName());
        if (item == null || item.getQuantity() < 1) {
            throw  new ItemNotFoundException(addCartItemsDTO.getItemName());
        }
        // create new cartItem object
        Optional<Cart> myCart = cartRepository.findByUserAndItem(user, item);

        if (myCart.isPresent()) {
            Cart cart = myCart.get();
            cart.setQuantity(cart.getQuantity() + 1);
            cart.setTotalCost(cart.getUnitCost() * cart.getQuantity());
            cartRepository.save(cart);
        } else {
            Cart newCart = new Cart();
            newCart.setItem(item);
            newCart.setUser(user);
            newCart.setQuantity(1);
            newCart.setUnitCost(item.getPrice());
            newCart.setTotalCost(newCart.getUnitCost() * newCart.getQuantity());
            cartRepository.save(newCart);
            userRepository.save(user);
        }
        item.setQuantity(item.getQuantity() - 1);
        itemRepository.save(item);
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

        itemRepository.save(newItem);
        userRepository.save(newUser);
    }

    @Override
    public Set<Cart> viewItemsInCart(EmailAddressDTO emailAddressDTO) {
        User user = userRepository.findByEmailAddress(emailAddressDTO.getEmailAddress());
        if (user == null) {
            throw new UserNameNotFoundException(emailAddressDTO.getEmailAddress());
        }
//        Cart userCart = user.getCart();
//        if (userCart == null) {
//            throw new IllegalArgumentException("Cart is empty at the moment");
//        }
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<String> placeOrder(EmailAddressDTO emailAddressDTO) {
        User user = userRepository.findByEmailAddress(emailAddressDTO.getEmailAddress());
        if (user == null) {
            throw new UserNameNotFoundException(emailAddressDTO.getEmailAddress());
        }
//        Cart cartToOrder = user.getCart();
//        if (cartToOrder == null) {
//            throw new IllegalArgumentException("Cart is empty, Please select items first");
//        }
        //Proceed to checkout

        //generate order tracking code
        String token = generator.createOrderTrackingCode();
        if (token == null) {
            throw new IllegalArgumentException("Problem encountered while generating token");
        }
        OTP OTP = new OTP();
        OTP.setUser(user);
        OTP.setCreatedAt(LocalDateTime.now());
        OTP.setConfirmedAt(null);
        OTP.setToken(token);
        OTP.setExpiresAt(LocalDateTime.now().plusDays(365));
        // save items in cart to pending orders table
        PendingOrder pendingOrder = new PendingOrder();
        pendingOrder.setUser(user);
//        pendingOrder.setCart(cartToOrder);
        pendingOrder.setToken(token);
        pendingOrder.setCreatedOn(new Date());
        //update user field
        user.addPendingOrder(pendingOrder);
        user.setAuthenticationToken(token);

        // Get total price of items in cart
        double totalPrice =9;

        //send order details email
        emailService.sendOrderTrackingMail(emailAddressDTO.getEmailAddress(),token);

//        //reset fields and persist
//        user.resetUserCart();
//        pendingOrderRepository.save(pendingOrder);
//        otpRepository.save(OTP);
//        cartRepository.save(cartToOrder);
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
        OTP confirmationToken = otpRepository.findByToken(token)
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
        if (user1 == null) {
            throw new UserNameNotFoundException(dto.getEmailAddress());
        }
        Set<UserRole> roles = user1.getRoles();
        List<String> myRoles = new ArrayList<>();
        for (UserRole role: roles) {
           myRoles.add(role.getTitle());
        }
        return myRoles;
    }

}
