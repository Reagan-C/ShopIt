package com.reagan.shopIt.repository;

import com.reagan.shopIt.model.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Add custom query methods if needed
}
