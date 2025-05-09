package com.technova.shopverse.controller;

import com.technova.shopverse.model.Category;
import com.technova.shopverse.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping
    public List<Category> getAll(){

        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id){

        return categoryRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Category create(@RequestBody Category category){

        return categoryRepository.save(category);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category categoryDetails){

        Category category = categoryRepository.findById(id).orElse(null);

            if(category != null){
                category.setName(categoryDetails.getName());
                category.setDescription(categoryDetails.getDescription());

                return categoryRepository.save(category);
            }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){

        categoryRepository.deleteById(id);

    }

}
