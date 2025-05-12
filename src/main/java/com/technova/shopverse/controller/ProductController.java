package com.technova.shopverse.controller;

import com.technova.shopverse.model.Product;
import com.technova.shopverse.service.ProductService;
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
    public ResponseEntity<List<Product>> getAll() {

        List<Product> products = productService.getAllProducts();

        if(products.isEmpty()){

            return ResponseEntity.noContent().build(); //204
        }else{

            return ResponseEntity.ok(products); //200
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id){

        return productService.getProductById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); //200 o 404
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product){

        try{
            Product createProduct = productService.createProduct(product);
            URI location = URI.create("/api/products" + createProduct.getId());

            return ResponseEntity.created(location).body(createProduct); //201
        }catch (IllegalArgumentException e){

            return ResponseEntity.badRequest().build(); //400
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product){

        try{
            Product updateProduct = productService.updateProduct(id, product);

            return ResponseEntity.ok(updateProduct); //200
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build(); //404
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        try{
            productService.deleteProduct(id);

            return ResponseEntity.noContent().build(); //204
        }catch (IllegalArgumentException e){

            return ResponseEntity.notFound().build(); //404
        }
    }

}
