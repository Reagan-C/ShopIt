package com.reagan.shopIt.service.ServiceImpl;

import com.reagan.shopIt.config.security.jwt.JwtTokenProvider;
import com.reagan.shopIt.model.domain.*;
import com.reagan.shopIt.model.dto.cartdto.AddCartItemsDTO;
import com.reagan.shopIt.model.dto.cartdto.OrderCartItemsDTO;
import com.reagan.shopIt.model.dto.onetimepassword.OneTimePasswordDTO;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ModelMapper mapper;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private FulfilledOrderRepository fulfilledOrderRepository;

    private PendingOrderRepository pendingOrderRepository;

    private final OTPRepository otpRepository;

    private final RoleRepository roleRepository;

    private final OTPGenerator generator;

    private final CountryRepository countryRepository;

    private final ItemRepository itemRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, FulfilledOrderRepository fulfilledOrderRepository,
                           PendingOrderRepository pendingOrderRepository, OTPRepository otpRepository,
                           RoleRepository roleRepository, OTPGenerator generator, CountryRepository countryRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.fulfilledOrderRepository = fulfilledOrderRepository;
        this.pendingOrderRepository = pendingOrderRepository;
        this.otpRepository = otpRepository;
        this.roleRepository = roleRepository;
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

        User newUser = mapper.map(body, User.class);
        newUser.setPassword(passwordEncoder.encode(body.getPassword()));
        newUser.addRole(roleRepository.findByTitle(UserRoleType.REGULAR.getValue()));

        //check if country exists and assign to user
        Country country = countryRepository.findByTitle(body.getNationality());
        if (country != null) {
            newUser.setCountry(country);
        } else {
            throw new IllegalStateException("Country doesn't exist by this title");
        }

        //Generate and save confirmation token
        String token = generator.createVerificationToken();
        AuthToken authToken = new AuthToken();
        authToken.setToken(token);
        authToken.setCreatedAt(LocalDateTime.now());
        authToken.setExpiresAt(LocalDateTime.now().plusMinutes(20));
        authToken.setUser(newUser);

        // create link
        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;

        // Send email notification and save user and otp
        emailService.sendAccountConfirmationMail(newUser.getEmailAddress(),
                emailService.buildEmail(newUser.getFirstName(), link));
        userRepository.save(newUser);
        otpRepository.save(authToken);
        return ResponseEntity.ok("Account created, Please log into your Email to confirm your account");
    }

    @Override
    @Transactional
    public ResponseEntity<String> confirmSignUpToken(OneTimePasswordDTO token) {
        User user1;

        //check if token exists
        AuthToken confirmationToken = otpRepository.findByToken(token.toString())
                .orElseThrow(InvalidOtpException::new);

        //check if token is confirmed
        if (confirmationToken.getConfirmedAt() != null) {
            throw new ConfirmedOtpException();
        }

        //check if token has expired, and remove user if not enabled
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            user1 = userRepository.findByEmailAddress(confirmationToken.getUser().getEmailAddress());
            if (user1.getEnabled().equals(false)) {
                userRepository.delete(user1);
            }
            throw new ExpiredOtpException();
        }

        //if valid, set confirmed at. Also set user enabled to true and send welcome mail
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        user1 = userRepository.findByEmailAddress(confirmationToken.getUser().getEmailAddress());
        user1.setEnabled(true);
        emailService.sendWelcomeMessage(user1.getEmailAddress());

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
        ResponseCookie cookie = tokenProvider.generateJwtCookie(userDetails);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("Login Successful");
    }

    @Override
    public ResponseEntity<?> signOut() {
        ResponseCookie cookie = tokenProvider.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(
                "You have been signed out"
        );
    }


    @Override
    public String sendChangePasswordOtp(ForgotPasswordDTO email) {
        User user2 = userRepository.findByEmailAddress(email.getEmailAddress());
        if (user2 != null) {
            String token = generator.createPasswordResetToken();
            AuthToken authToken = new AuthToken();
            authToken.setToken(token);
            authToken.setCreatedAt(LocalDateTime.now());
            authToken.setExpiresAt(LocalDateTime.now().plusMinutes(20));
            authToken.setUser(user2);
            otpRepository.save(authToken);
            emailService.sendChangePasswordMail(email.getEmailAddress(), token);
        }
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
        AuthToken confirmationToken = otpRepository.findByToken(resetPasswordDTO.getOtp().toString())
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
        //send email to user
        emailService.sendSuccessfulPasswordChangeMail(resetPasswordDTO.getEmailAddress());
        return ResponseEntity.ok("Password change successful");
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateUser(UpdateUserDTO updateUserDTO) {
       boolean userExists = userRepository.existsByEmailAddress(updateUserDTO.getEmailAddress());
       if (!userExists) {
           throw new UserNameNotFoundException(updateUserDTO.getFirstName()+ " " + updateUserDTO.getLastName());
       }
       User user =
       userRepository.updateUser(updateUserDTO.getFirstName(), updateUserDTO.getLastName(),
               updateUserDTO.getAddress(), updateUserDTO.getCity(), updateUserDTO.getState(),
               updateUserDTO.getNationality(), updateUserDTO.getPhoneNumber(), updateUserDTO.getEmailAddress());
        return ResponseEntity.ok("Hello " + user.getFirstName() + ", Your update operation is successful");
    }

    @Override
    public ResponseEntity<User> findUserByEmail(String emailAddress) {
         User user = userRepository.findByEmailAddress(emailAddress);
         if (user == null) {
             throw new UserNameNotFoundException(emailAddress);
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
        itemRepository.save(item);
    }

    @Override
    public void RemoveItemFromCart(OrderCartItemsDTO removeItemDTO) {
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
        itemRepository.save(newItem);
    }

    @Override
    public Set<CartItem> viewItemsInCart(String emailAddress) {
        User user = userRepository.findByEmailAddress(emailAddress);
        if (user == null) {
            throw new UserNameNotFoundException(emailAddress);
        }
        Cart userCart = user.getCart();
        if (userCart == null) {
            throw new IllegalArgumentException("Cart is empty at the moment");
        }
        return userCart.getCartItems();
    }

    @Override
    public ResponseEntity<String> placeOrder(OrderCartItemsDTO cartItems) {
        return null;
    }

    @Override
    public ResponseEntity<String> confirmOrderReception(OneTimePasswordDTO token) {
        return null;
    }
}
