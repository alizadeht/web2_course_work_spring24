package com.example.shoppingapp.repository;

import com.example.shoppingapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
