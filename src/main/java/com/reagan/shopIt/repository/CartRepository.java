package com.reagan.shopIt.repository;

import com.reagan.shopIt.model.domain.Cart;
import com.reagan.shopIt.model.domain.Item;
import com.reagan.shopIt.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "select i from Cart i", nativeQuery = true)
    List<Cart> getAllCartItems();

//    @Query(value = "select * from cart c where c.id=:id", nativeQuery = true)
//    Cart findByCartId(@Param("id") Long id);

    List<Cart> findAllByUserId(Long userId);

    Optional<Cart> findByUserAndItem(User user, Item item);


    void deleteByUserId(Long user_id);
}
