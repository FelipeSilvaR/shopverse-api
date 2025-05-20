package com.technova.shopverse.controller;

import com.technova.shopverse.dto.ProductDTO;
import com.technova.shopverse.model.Product;
import com.technova.shopverse.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {

        List<ProductDTO> products = productService.getAllProductsDTOs();

        if(products.isEmpty()){

            return ResponseEntity.noContent().build(); //204
        }else{

            return ResponseEntity.ok(products); //200
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id){

        return productService.getProductById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); //200 o 404
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody Product product){

        try{
            ProductDTO createProduct = productService.createProduct(product);


            return ResponseEntity.status(201).body(createProduct); //201
        }catch (IllegalArgumentException e){

            return ResponseEntity.badRequest().build(); //400
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @PathVariable Long id, @RequestBody Product product){

        try{
            ProductDTO updateProduct = productService.updateProduct(id, product);

            return ResponseEntity.ok(updateProduct); //200
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build(); //404
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){

        try{
            productService.deleteProduct(id);

            return ResponseEntity.noContent().build(); //204
        }catch (IllegalArgumentException e){

            return ResponseEntity.notFound().build(); //404
        }
    }

    @GetMapping("/dto")
    public ResponseEntity<List<ProductDTO>> getAllWithCategory(){

        List<ProductDTO> dtoList = productService.getAllProductsDTOs();

        if(dtoList.isEmpty()){

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/by-category/{categoryId}")

    public ResponseEntity<List<ProductDTO>> getByCategory(@PathVariable Long categoryId) {

        List<ProductDTO> products = productService.getByCategoryId(categoryId);

        if (products.isEmpty()) {

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(products);
    }

}
