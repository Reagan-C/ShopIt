package com.reagan.shopIt.service.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reagan.shopIt.model.domain.Category;
import com.reagan.shopIt.model.exception.CategoryExistsException;
import com.reagan.shopIt.repository.CategoryRepository;
import com.reagan.shopIt.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.reagan.shopIt.util.ShopItUtil.readResourceFile;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    @TransactionalEventListener(ApplicationReadyEvent.class)
    public void seedCategory() throws JsonProcessingException {
        String categoryAsString = readResourceFile("json/categories.json");
        ObjectMapper objectMapper = new ObjectMapper();

        Category[] categoryArray = objectMapper.readValue(categoryAsString, Category[].class);
        List<Category> categoryList = Arrays.asList(categoryArray);

        try {
            for (Category category : categoryList) {
                if (false) {
                    Optional<Category> categoryExists = categoryRepository.findByNameAndAbbreviation(
                            category.getName(), category.getAbbreviation());

                    if (categoryExists.isPresent()) {
                        continue;
                    }

                    category.setId(null);
                    categoryRepository.save(category);
                }
            }
        }catch (CategoryExistsException e){

        }
    }
}
