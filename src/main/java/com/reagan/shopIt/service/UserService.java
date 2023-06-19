package com.reagan.shopIt.service;

import com.reagan.shopIt.model.domain.User;
import com.reagan.shopIt.model.dto.UserDTO;

public interface UserService {
    User getUserById(Long userId);
    UserDTO createUser(UserDTO userDTO);

    UserDTO signIn(String username, String password);

    UserDTO updateUser(UserDTO userDTO);

    void deleteUser(Long id);


    // Add more methods for user-related operations
}
