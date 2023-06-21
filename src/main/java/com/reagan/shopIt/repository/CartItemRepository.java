package com.reagan.shopIt.repository;

import com.reagan.shopIt.model.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // Add custom query methods if needed
}
