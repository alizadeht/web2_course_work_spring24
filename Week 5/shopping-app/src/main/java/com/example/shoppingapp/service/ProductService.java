package com.example.shoppingapp.service;

import com.example.shoppingapp.model.Product;
import com.example.shoppingapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(Long productId) {
        return productRepository.findById(productId);

    }

    // Other CRUD operations
}
