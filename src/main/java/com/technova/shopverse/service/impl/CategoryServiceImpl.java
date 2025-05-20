package com.technova.shopverse.service.impl;


import com.technova.shopverse.dto.CategoryDTO;
import com.technova.shopverse.dto.ProductDTO;
import com.technova.shopverse.model.Category;
import com.technova.shopverse.model.Product;
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

    @Override
    public List<CategoryDTO> getAllCategories(){

        return categoryRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public Optional<CategoryDTO> getCategoryById(Long id){

        return categoryRepository.findById(id).map(this::toDTO);
    }

    @Override
    public CategoryDTO createCategory(Category category){

        if (category.getName() == null || category.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre de la categoria no puede estar vacío.");
        }
        if (category.getDescription().length() < 10 ) {
            throw new IllegalArgumentException("La descripción de tener al menos 10 caracteres.");
        }

        Category categoryCreated = categoryRepository.save(category);


        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                null
        );
    }

    @Override
    public CategoryDTO updateCategory(Long id, Category update){

        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if(optionalCategory.isEmpty()){

            throw new IllegalArgumentException("La categoria con ID " + id + " no existe.");
        }

        Category category = optionalCategory.get();
        category.setName(update.getName());
        category.setDescription(update.getDescription());

        Category categoryUpdate = categoryRepository.save(category);

        return toDTO(categoryUpdate);
    }

    @Override
    public void deleteCategory(Long id){

        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDTO getCategoryDTOById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        return toDTO(category);
    }

    public CategoryDTO toDTO(Category category){

        List<String> productNames = category.getProducts().stream()
                .map(Product::getName)
                .toList();

        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                productNames
        );
    }

}
