package com.reagan.shopIt;

import com.reagan.shopIt.model.domain.Products;
import com.reagan.shopIt.model.domain.User;
import com.reagan.shopIt.model.dto.CategoryDTO;
import com.reagan.shopIt.repository.ProductRepository;
import com.reagan.shopIt.repository.UserRepository;
import com.reagan.shopIt.service.ProductService;
import com.reagan.shopIt.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Configuration
public class DTOTests {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;
    @Test
    public void testProductDTO() {
        // Create a ProductDTO object
        ProductDTO productDTO = new ProductDTO(1L, "Product 1", "Description 1", 9.99, 10, "image.jpg", 1L);

        // Test the getters
        assertThat(productDTO.getProductId()).isEqualTo(1L);
        assertThat(productDTO.getName()).isEqualTo("Product 1");
        assertThat(productDTO.getDescription()).isEqualTo("Description 1");
        assertThat(productDTO.getPrice()).isEqualTo(9.99);
        assertThat(productDTO.getQuantity()).isEqualTo(10);
        assertThat(productDTO.getPicture()).isEqualTo("image.jpg");
        assertThat(productDTO.getCategoryId()).isEqualTo(1L);

        // Test the setters
        productDTO.setPrice(19.99);
        assertThat(productDTO.getPrice()).isEqualTo(19.99);
    }

    @Test
    public void testCategoryDTO() {
        // Create a CategoryDTO object
        CategoryDTO categoryDTO = new CategoryDTO(1L, "Category 1");

        // Test the getters
        assertThat(categoryDTO.getCategoryId()).isEqualTo(1L);
        assertThat(categoryDTO.getName()).isEqualTo("Category 1");

        // Test the setters
        categoryDTO.setName("Updated Category");
        assertThat(categoryDTO.getName()).isEqualTo("Updated Category");
    }

    // Repeat the same pattern for UserDTO, OrderDTO, and OrderItemDTO

    // ...
    @Test
    public void testCreateUser_WhenUserDoesNotExist_ShouldCreateUser() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        // Set userDTO properties

        Mockito.when(userRepository.existSByUsername(userDTO.getUsername())).thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L); // Set a unique ID for the created user
            return user;
        });

        // Act
        UserDTO createdUser = userService.createUser(userDTO);

        // Assert
        assertNotNull(createdUser);
        assertEquals(userDTO.getUsername(), createdUser.getUsername());
        // Assert other properties as needed
    }

    @Test
    public void testCreateProduct_WhenProductDoesNotExist_ShouldCreateProduct() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        // Set productDTO properties

        Mockito.when(productRepository.save(Mockito.any(Products.class))).thenAnswer(invocation -> {
            Products products = invocation.getArgument(0);
            products.setId(1L); // Set a unique ID for the created product
            return products;
        });

        // Act
        ProductDTO createdProduct = productService.createProduct(productDTO);

        // Assert
        assertNotNull(createdProduct);
        assertEquals(productDTO.getName(), createdProduct.getName());
        // Assert other properties as needed
    }



}

