package com.reagan.shopIt.service.ServiceImpl;

import com.reagan.shopIt.model.domain.Category;
import com.reagan.shopIt.model.dto.categorydto.AddCategoryDTO;
import com.reagan.shopIt.model.dto.categorydto.CategoryNameDTO;
import com.reagan.shopIt.model.exception.CategoryExistsException;
import com.reagan.shopIt.model.exception.CategoryNotFoundException;
import com.reagan.shopIt.repository.CategoryRepository;
import com.reagan.shopIt.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    @Transactional
    public ResponseEntity<?> addNewCategory(AddCategoryDTO addCategoryDTO) {
        Category category1 = categoryRepository.findByName(addCategoryDTO.getName());
        if (category1 != null) {
            throw new CategoryExistsException();
        }
        Category category = new Category();
        category.setName(addCategoryDTO.getName());
        category.setAbbreviation(addCategoryDTO.getAbbreviation());

        categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Category saved");
    }

    @Override
    @Transactional
    public ResponseEntity<?> removeCategory(CategoryNameDTO categoryName) {
        Category existingCategory = categoryRepository.findByName(categoryName.getName());
        if (existingCategory == null) {
            throw new CategoryNotFoundException(categoryName.getName());
        }
        existingCategory.removeItemFromCategory();
        categoryRepository.delete(existingCategory);

        return ResponseEntity.status(HttpStatus.OK).body("Category removed");
    }

    @Override
    public List<Category> findAllCategories() {
        List<Category> categories = categoryRepository.getAllCategories();

        if (categories.isEmpty()) {
            throw new IllegalArgumentException("Category list is empty");
        }

        return categories;
    }

    @Override
    public Category findCategoryByName(CategoryNameDTO categoryNameDTO) {
        Category category = categoryRepository.findByName(categoryNameDTO.getName());
        if (category == null) {
            throw new CategoryNotFoundException(categoryNameDTO.getName());
        }
        return  category;
    }
}
