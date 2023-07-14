package com.reagan.shopIt.service;

import com.reagan.shopIt.model.domain.Category;
import com.reagan.shopIt.model.dto.categorydto.AddCategoryDTO;
import com.reagan.shopIt.model.dto.categorydto.UpdateCategoryDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    @Transactional
    ResponseEntity<?> addNewCategory(AddCategoryDTO addCategoryDTO);

    @Transactional
    ResponseEntity<?> removeCategory(Long categoryId);

    List<Category> findAllCategories();

    List<Map<String, Object>> findCategoryById(Long id);

    List<Object[]> getAllItemsInCategory(Long categoryId);

    @Transactional
    @Modifying
    ResponseEntity<String> updateCategoryDetails(Long id, UpdateCategoryDTO body);

}
