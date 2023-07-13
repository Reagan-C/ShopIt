package com.reagan.shopIt.repository;

import com.reagan.shopIt.model.domain.PendingOrder;
import com.reagan.shopIt.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PendingOrderRepository extends JpaRepository<PendingOrder, Long> {

    @Query(value = "select po from PendingOrder po where po.user.id = :id order by po.createdOn desc",
    nativeQuery = true)
    List<PendingOrder> getPendingOrders(@Param("id") Long user_id);

    @Query(value = "select i from PendingOrder i", nativeQuery = true)
    List<PendingOrder> getAllOrders();

    PendingOrder findByToken(String token);

    Optional<PendingOrder> findByIdAndUserId(Long id, Long userId);
}
