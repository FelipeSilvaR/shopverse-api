package com.technova.shopverse.service;

import com.technova.shopverse.model.Category;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    public List<Category> getAllCategories();
    public Optional<Category> getCategoryById(@PathVariable Long id);
    public Category createCategory(@RequestBody Category category);
    public Category updateCategory(@PathVariable Long id, @RequestBody Category update);
    public void deleteCategory(@PathVariable Long id);
}
