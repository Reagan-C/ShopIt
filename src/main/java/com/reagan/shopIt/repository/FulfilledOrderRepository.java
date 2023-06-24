package com.reagan.shopIt.repository;

import com.reagan.shopIt.model.domain.FulfilledOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FulfilledOrderRepository extends JpaRepository<FulfilledOrders, Long> {

}
