package com.technova.shopverse.service.impl;


import com.technova.shopverse.dto.ProductDTO;
import com.technova.shopverse.model.Product;
import com.technova.shopverse.repository.ProductRepository;
import com.technova.shopverse.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductDTO> getAllProductsDTOs() {

        return productRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public Optional<ProductDTO> getProductById(Long id) {

        return productRepository.findById(id).map(this::toDTO);
    }

    @Override
    public ProductDTO createProduct(Product product) {

        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        }

        Product productCreated = productRepository.save(product);

        return toDTO(productCreated);
    }

    @Override
    public ProductDTO updateProduct(Long id, Product updated) {

        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
        }

        Product product = optionalProduct.get();
        product.setName(updated.getName());
        product.setDescription(updated.getDescription());
        product.setPrice(updated.getPrice());

        Product savedProduct = productRepository.save(product);

        return toDTO(savedProduct);
    }

    @Override
    public void deleteProduct(Long id) {

        productRepository.deleteById(id);
    }

    public ProductDTO toDTO(Product product){

        String categoryName = product.getCategory() != null ? product.getCategory().getName() : null;

        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                categoryName
        );
    }

    @Override
    public List<ProductDTO> getByCategoryId(Long categoryId) {

        return productRepository.findByCategoryId(categoryId).stream().map(this::toDTO).toList();
    }
}
