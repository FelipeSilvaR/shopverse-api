package com.technova.shopverse.controller;

import com.technova.shopverse.dto.CategoryDTO;
import com.technova.shopverse.model.Category;
import com.technova.shopverse.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAll(){

        List<CategoryDTO> categories = categoryService.getAllCategories();

        if(categories.isEmpty()){

            return ResponseEntity.noContent().build(); //204
        }else{

            return ResponseEntity.ok(categories); //200
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable Long id){

        return categoryService.getCategoryById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); //200 o 404
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody Category category){

        try{
            CategoryDTO createCategory = categoryService.createCategory(category);

            return ResponseEntity.status(201).body(createCategory);
        }catch (IllegalArgumentException e){

            return ResponseEntity.badRequest().build(); //400
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @PathVariable Long id, @RequestBody Category category){

        try{
            CategoryDTO updateCategory = categoryService.updateCategory(id, category);

            return ResponseEntity.ok(updateCategory); //200
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build(); //404
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){

        try{
            categoryService.deleteCategory(id);

            return ResponseEntity.noContent().build(); //204
        }catch (IllegalArgumentException e){

            return ResponseEntity.notFound().build(); //404
        }
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<CategoryDTO> getCategoryDetails(@PathVariable Long id) {
        try {
            CategoryDTO dto = categoryService.getCategoryDTOById(id);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
