package com.pankbuto.ecommerce.repository;

import com.pankbuto.ecommerce.models.Order;
import com.pankbuto.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllOrdersByUser(User user);
}
