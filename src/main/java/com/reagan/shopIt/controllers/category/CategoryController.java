package com.reagan.shopIt.controllers.category;

import com.reagan.shopIt.model.domain.Category;
import com.reagan.shopIt.model.domain.Item;
import com.reagan.shopIt.model.dto.categorydto.AddCategoryDTO;
import com.reagan.shopIt.model.dto.categorydto.UpdateCategoryDTO;
import com.reagan.shopIt.service.CategoryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "category", produces = {MediaType.APPLICATION_JSON_VALUE},
                consumes = {MediaType.APPLICATION_JSON_VALUE})
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('Administrator')")
    @PostMapping("/add-category")
    public ResponseEntity<?> addCategory(@Validated @RequestBody AddCategoryDTO categoryDTO) {
        return categoryService.addNewCategory(categoryDTO);
    }

    @PreAuthorize("hasRole('Administrator')")
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeCategory(@RequestParam("category_id") Long categoryId) {
        return categoryService.removeCategory(categoryId);
    }

    @GetMapping("/get-all")
    public List<Category> getAllCategories() {
        return categoryService.findAllCategories();
    }

    @PreAuthorize("hasRole('Administrator')")
    @GetMapping("/get")
    public List<Map<String, Object>> getById(@RequestParam("id") Long categoryId) {
        return categoryService.findCategoryById(categoryId);
    }

    @GetMapping("/get-items")
    public List<Object[]> getAllItemsInCategory(@RequestParam("id") Long id) {
        return categoryService.getAllItemsInCategory(id);
    }

    @PreAuthorize("hasRole('Administrator')")
    @PutMapping("/update")
    public ResponseEntity<String> updateCategory(@RequestParam("id")Long id,
                                                 @Validated @RequestBody UpdateCategoryDTO body) {
        return categoryService.updateCategoryDetails(id, body);
    }
}
