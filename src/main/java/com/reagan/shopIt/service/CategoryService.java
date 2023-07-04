package com.reagan.shopIt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.reagan.shopIt.model.domain.Category;
import com.reagan.shopIt.model.dto.categorydto.AddCategoryDTO;
import com.reagan.shopIt.model.dto.categorydto.CategoryNameDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    @Transactional
    ResponseEntity<?> addNewCategory(AddCategoryDTO addCategoryDTO);

    @Transactional
    ResponseEntity<?> removeCategory(CategoryNameDTO categoryName);

    List<Category> findAllCategories();

    Category findCategoryByName(CategoryNameDTO categoryNameDTO);

}
