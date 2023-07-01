package com.reagan.shopIt.controllers.category;

import com.reagan.shopIt.model.domain.Category;
import com.reagan.shopIt.model.dto.categorydto.AddCategoryDTO;
import com.reagan.shopIt.model.dto.categorydto.CategoryNameDTO;
import com.reagan.shopIt.service.CategoryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> removeCategory(@Validated @RequestBody CategoryNameDTO categoryDTO) {
        return categoryService.removeCategory(categoryDTO);
    }

    @GetMapping("/get-all")
    public List<Category> getAllCategories() {
        return categoryService.findAllCategories();
    }

    @GetMapping("/get")
    public Category getByName(@Validated @RequestBody CategoryNameDTO categoryNameDTO) {
        return categoryService.findCategoryByName(categoryNameDTO);
    }

}
