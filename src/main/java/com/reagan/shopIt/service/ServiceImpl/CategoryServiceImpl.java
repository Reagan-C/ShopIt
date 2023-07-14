package com.reagan.shopIt.service.ServiceImpl;

import com.reagan.shopIt.model.domain.Category;
import com.reagan.shopIt.model.dto.categorydto.AddCategoryDTO;
import com.reagan.shopIt.model.dto.categorydto.UpdateCategoryDTO;
import com.reagan.shopIt.model.exception.CategoryExistsException;
import com.reagan.shopIt.model.exception.CategoryIDNotFoundException;
import com.reagan.shopIt.repository.CategoryRepository;
import com.reagan.shopIt.repository.ItemRepository;
import com.reagan.shopIt.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ItemRepository itemRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
    }


    @Override
    @Transactional
    public ResponseEntity<?> addNewCategory(AddCategoryDTO addCategoryDTO) {
        //check if category already exists by either name or abbreviation;
        Category category1 = categoryRepository.findByName(addCategoryDTO.getName());
        Category category2 = categoryRepository.findByAbbreviation(addCategoryDTO.getAbbreviation());

        if (category1 != null || category2 != null) {
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
    public ResponseEntity<?> removeCategory(Long id) {
       Category category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryIDNotFoundException(id)
       );

       categoryRepository.delete(category);
       return ResponseEntity.status(HttpStatus.OK).body("Category with name "
               + category.getName() + " successfully removed");
    }

    @Override
    public List<Category> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            throw new IllegalArgumentException("Category list is empty");
        }

        return categories;
    }

    @Override
    public List<Map<String, Object>> findCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryIDNotFoundException(id)
        );

        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> categoryMap = new LinkedHashMap<>();

        categoryMap.put("id", category.getId());
        categoryMap.put("name", category.getName());
        categoryMap.put("abbreviation", category.getAbbreviation());
        categoryMap.put("created_on", category.getCreatedOn());
        categoryMap.put("updated_on", category.getUpdatedOn());

        mapList.add(categoryMap);
        return mapList;
    }

    @Override
    public List<Object[]> getAllItemsInCategory(Long categoryId) {
       Category category = categoryRepository.findById(categoryId).orElseThrow(
               () -> new CategoryIDNotFoundException(categoryId)
       );

       List<Object[]> items = itemRepository.getItemsByCategoryId(categoryId);
       if (items.isEmpty()) {
           throw new IllegalArgumentException("we currently do not have any items in this category");
       }

        return items;
    }

    @Override
    public ResponseEntity<String> updateCategoryDetails(Long id, UpdateCategoryDTO body) {
        Category categoryToUpdate = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryIDNotFoundException(id)
        );

        categoryToUpdate.setName(body.getName());
        categoryToUpdate.setAbbreviation(body.getAbbreviation());

        categoryRepository.save(categoryToUpdate);
        return ResponseEntity.ok("updated successfully");
    }
}
