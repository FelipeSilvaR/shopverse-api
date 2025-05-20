package com.technova.shopverse.service;

import com.technova.shopverse.dto.CategoryDTO;
import com.technova.shopverse.model.Category;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();
    Optional<CategoryDTO> getCategoryById(@PathVariable Long id);
    CategoryDTO createCategory(@RequestBody Category category);
    CategoryDTO updateCategory(@PathVariable Long id, @RequestBody Category update);
    void deleteCategory(@PathVariable Long id);
    CategoryDTO getCategoryDTOById(Long id);
}
