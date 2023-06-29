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

    @Query(value = "select i from Item i where i.category.category_name = :name order by i.createdOn desc",
    nativeQuery = true)
    List<Item> getItemsByCategoryName(@Param("name") String categoryName);

    @Query(value = "select i FROM Item i where i.category.category_name = :name AND (i.createdOn BETWEEN :from AND :to)",
    nativeQuery = true)
    Page<Item> getItems(Date from, Date to, String name, Pageable pageable);

    @Query(value = "select i from Item i", nativeQuery = true)
    List<Item> getAllItems();

    Item findByName(String name);

    Boolean existsByName(String name);
}
