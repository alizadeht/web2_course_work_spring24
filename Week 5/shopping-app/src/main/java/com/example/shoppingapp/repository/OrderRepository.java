package com.example.shoppingapp.repository;

import com.example.shoppingapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}
