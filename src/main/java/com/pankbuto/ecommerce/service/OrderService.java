package com.pankbuto.ecommerce.service;

import com.pankbuto.ecommerce.models.Order;
import com.pankbuto.ecommerce.models.User;
import com.pankbuto.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders(User user) {
        return this.orderRepository.findAllOrdersByUser(user);
    }
}
