package com.example.shoppingapp.service;

import com.example.shoppingapp.model.Order;
import com.example.shoppingapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> listAllOrders() {
        return orderRepository.findAll();
    }

    // Other CRUD operations
}
