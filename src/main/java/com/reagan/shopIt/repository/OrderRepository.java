package com.reagan.shopIt.repository;

import com.reagan.shopIt.model.domain.PendingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<PendingOrder, Long> {
    // Add custom query methods if needed
}
