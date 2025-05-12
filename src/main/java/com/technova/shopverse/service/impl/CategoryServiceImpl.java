package com.technova.shopverse.service.impl;


import com.technova.shopverse.model.Category;
import com.technova.shopverse.repository.CategoryRepository;
import com.technova.shopverse.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<Category> getAllCategories(){

        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Category> getCategoryById(@PathVariable Long id){

        return categoryRepository.findById(id);
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category){

        if (category.getName() == null || category.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre de la categoria no puede estar vacío.");
        }
        if (category.getDescription().length() < 10 ) {
            throw new IllegalArgumentException("La descripción de tener al menos 10 caracteres.");
        }

        return categoryRepository.save(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category update){

        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if(optionalCategory.isEmpty()){

            throw new IllegalArgumentException("La categoria con ID " + id + " no existe.");
        }

        Category category = optionalCategory.get();
        category.setName(update.getName());
        category.setDescription(update.getDescription());

        return categoryRepository.save(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){

        categoryRepository.deleteById(id);
    }
}
