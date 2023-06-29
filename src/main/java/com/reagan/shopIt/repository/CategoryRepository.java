package com.reagan.shopIt.repository;

import com.reagan.shopIt.model.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "select i from Category i", nativeQuery = true)
    List<Category> getAllCategories();

    Optional<Category> findByNameAndAbbreviation(String name, String abbreviation);

    Category findByName(String name);

}
