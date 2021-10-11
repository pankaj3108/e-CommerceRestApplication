package com.pankbuto.ecommerce.controller;

import com.pankbuto.ecommerce.models.Order;
import com.pankbuto.ecommerce.models.User;
import com.pankbuto.ecommerce.service.AuthenticationService;
import com.pankbuto.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/all")
    public ResponseEntity<List<Order>> findAllOrders(@RequestParam("token") String token) throws Exception {
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        List<Order> allOrderList = this.orderService.getAllOrders(user);

        return new ResponseEntity<List<Order>>(allOrderList, HttpStatus.OK);
    }

}
