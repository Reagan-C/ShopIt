package com.reagan.shopIt.repository;

import com.reagan.shopIt.model.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "select i.name, i.picture, i.price  from items i where i.category_id = :id " +
            "order by i.added_on asc", nativeQuery = true)
    List<Object[]> getItemsByCategoryId(@Param("id") Long category_id);

    @Query(value = "select i.* FROM items i where i.category.category_name = :name AND (i.createdOn BETWEEN :from AND :to)",
    nativeQuery = true)
    Page<Item> getItems(Date from, Date to, String name, Pageable pageable);

    @Query(value = "select * from Items", nativeQuery = true)
    List<Item> getAllItems();

    Item findByName(String name);

    Boolean existsByName(String name);
}
