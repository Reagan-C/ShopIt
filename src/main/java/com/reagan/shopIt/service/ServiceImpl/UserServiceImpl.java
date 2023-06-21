package com.reagan.shopIt.service.ServiceImpl;

import com.reagan.shopIt.model.domain.User;
import com.reagan.shopIt.model.exception.UserAlreadyExistsException;
import com.reagan.shopIt.model.exception.UserNotFoundException;
import com.reagan.shopIt.repository.UserRepository;
import com.reagan.shopIt.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUserById(Long userId) {
        return null;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        boolean oldUser = userRepository.existSByUsername(userDTO.getUsername());
        if (oldUser) {
            throw new UserAlreadyExistsException();
        }
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO signIn(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user==null) {
           throw  new UserNotFoundException("Invalid username or password");
        }
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        User existingUser = userRepository.findById(userDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Update the user fields based on the provided userDTO
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        // Update other fields as needed
        if (userDTO.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        User savedUser = userRepository.save(existingUser);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public void deleteUser(Long id) {

    }

    // Implement other methods from the UserService interface
}