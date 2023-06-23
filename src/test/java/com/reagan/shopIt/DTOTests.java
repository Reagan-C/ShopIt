package com.reagan.shopIt;

import com.reagan.shopIt.model.domain.Admin;
import com.reagan.shopIt.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
//
@Configuration
public class DTOTests {

    @Test
    public void testUser() {
        Set<User> user = new HashSet<>();
        Admin admin = new Admin(1L,user);
        User user2 = new User();
        User user3 = new User();
        admin.addAdmin(user2);
        admin.addAdmin(user3);

        assertThat(admin.getId()).isEqualTo(1L);
        assertThat(admin.getUsers()).isEqualTo(user);
        assertThat(admin.getUsers().size()).isEqualTo(2);
    }

//    @Test
//    public void testCreateUser_WhenUserDoesNotExist_ShouldCreateUser() {
//        // Arrange
//        UserDTO userDTO = new UserDTO();
//        // Set userDTO properties
//
//        Mockito.when(userRepository.existSByUsername(userDTO.getUsername())).thenReturn(false);
//        Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer(invocation -> {
//            User user = invocation.getArgument(0);
//            user.setId(1L); // Set a unique ID for the created user
//            return user;
//        });
//
//        // Act
//        UserDTO createdUser = userService.createUser(userDTO);
//
//        // Assert
//        assertNotNull(createdUser);
//        assertEquals(userDTO.getUsername(), createdUser.getUsername());
//        // Assert other properties as needed
//    }

}

