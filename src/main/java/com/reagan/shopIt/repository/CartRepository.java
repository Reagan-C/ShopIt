package com.reagan.shopIt.repository;

import com.reagan.shopIt.model.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "select i from Cart i", nativeQuery = true)
    List<Cart> getAllCartItems();
}