package com.reagan.shopIt.service.ServiceImpl;

import com.reagan.shopIt.model.domain.AuthToken;
import com.reagan.shopIt.model.domain.Country;
import com.reagan.shopIt.model.domain.User;
import com.reagan.shopIt.model.dto.onetimepassword.OneTimePasswordDTO;
import com.reagan.shopIt.model.dto.userdto.SignUpDTO;
import com.reagan.shopIt.model.enums.UserRoleType;
import com.reagan.shopIt.model.exception.ConfirmedOtpException;
import com.reagan.shopIt.model.exception.ExpiredOtpException;
import com.reagan.shopIt.model.exception.InvalidOtpException;
import com.reagan.shopIt.model.exception.UserAlreadyExistsException;
import com.reagan.shopIt.repository.*;
import com.reagan.shopIt.service.EmailService;
import com.reagan.shopIt.service.UserService;
import com.reagan.shopIt.util.OTPGenerator;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ModelMapper mapper;
    private UserRepository userRepository;

    private FulfilledOrderRepository fulfilledOrderRepository;

    private PendingOrderRepository pendingOrderRepository;

    private OTPRepository otpRepository;

    private RoleRepository roleRepository;

    private  OTPGenerator generator;

    private  CountryRepository countryRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, FulfilledOrderRepository fulfilledOrderRepository,
                           PendingOrderRepository pendingOrderRepository, OTPRepository otpRepository,
                           RoleRepository roleRepository, OTPGenerator generator, CountryRepository countryRepository) {
        this.userRepository = userRepository;
        this.fulfilledOrderRepository = fulfilledOrderRepository;
        this.pendingOrderRepository = pendingOrderRepository;
        this.otpRepository = otpRepository;
        this.roleRepository = roleRepository;
        this.generator = generator;
        this.countryRepository = countryRepository;
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
            throw new IllegalStateException("Country doesnt exist by this title");
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
    public ResponseEntity<String> confirmToken(OneTimePasswordDTO token) {
        User user1;

        //check if token exists
        AuthToken confirmationToken = otpRepository.findByToken(token)
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
}
