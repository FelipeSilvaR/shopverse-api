package com.technova.shopverse.service;

import com.technova.shopverse.dto.ProductDTO;
import com.technova.shopverse.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDTO> getAllProductsDTOs();
    Optional<ProductDTO> getProductById(Long id);
    ProductDTO createProduct(Product product);
    ProductDTO updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    ProductDTO toDTO(Product product);
    List<ProductDTO> getByCategoryId(Long categoryId);
}
