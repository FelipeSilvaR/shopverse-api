package com.technova.shopverse.controller;

import com.technova.shopverse.model.Category;
import com.technova.shopverse.model.Product;
import com.technova.shopverse.service.CategoryService;
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
    public ResponseEntity<List<Category>> getAll(){

        List<Category> categories = categoryService.getAllCategories();

        if(categories.isEmpty()){

            return ResponseEntity.noContent().build(); //204
        }else{

            return ResponseEntity.ok(categories); //200
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id){

        return categoryService.getCategoryById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); //200 o 404
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category){

        try{
            Category createCategory = categoryService.createCategory(category);
            URI location = URI.create("/api/categories" + createCategory.getId());

            return ResponseEntity.created(location).body(createCategory); //201
        }catch (IllegalArgumentException e){

            return ResponseEntity.badRequest().build(); //400
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category category){

        try{
            Category updateCategory = categoryService.updateCategory(id, category);

            return ResponseEntity.ok(updateCategory); //200
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build(); //404
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        try{
            categoryService.deleteCategory(id);

            return ResponseEntity.noContent().build(); //204
        }catch (IllegalArgumentException e){

            return ResponseEntity.notFound().build(); //404
        }
    }

}
