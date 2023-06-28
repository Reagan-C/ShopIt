package com.reagan.shopIt.repository;

import com.reagan.shopIt.model.domain.AuthToken;
import com.reagan.shopIt.model.domain.FulfilledOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FulfilledOrderRepository extends JpaRepository<FulfilledOrders, Long> {

    @Query(value = "select fo from FulfilledOrders fo where fo.user.id = :id order by po.createdOn desc",
    nativeQuery = true)
    List<FulfilledOrders> getFulfilledOrders(@Param("id") Long user_id);

    @Query(value = "select i from FulfilledOrders i", nativeQuery = true)
    List<FulfilledOrders> getAllFulfilledOrders();

    FulfilledOrders findByToken(String token);
}
