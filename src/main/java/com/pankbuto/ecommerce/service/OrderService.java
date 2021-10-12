package com.pankbuto.ecommerce.service;

import com.pankbuto.ecommerce.dto.cart.CartDto;
import com.pankbuto.ecommerce.dto.cart.CartItemDto;
import com.pankbuto.ecommerce.models.Order;
import com.pankbuto.ecommerce.models.OrderItem;
import com.pankbuto.ecommerce.models.User;
import com.pankbuto.ecommerce.repository.OrderItemRepository;
import com.pankbuto.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<Order> getAllOrders(User user) {
        return this.orderRepository.findAllOrdersByUser(user);
    }

    public void placeOrder(User user) {
        CartDto cartDto = cartService.findAllCartItems(user);

        List<CartItemDto> cartItemDtoList = cartDto.getCartItems();

        Order newOrder = new Order();
        newOrder.setCreatedDate(new Date());
        newOrder.setUser(user);
        newOrder.setTotalPrice(cartDto.getTotalCost());

        orderRepository.save(newOrder);

        for(CartItemDto cartItemDto : cartItemDtoList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setCreatedDate(new Date());
            orderItem.setPrice(cartItemDto.getProduct().getPrice());
            orderItem.setProduct(cartItemDto.getProduct());
            orderItem.setQuantity(cartItemDto.getQuantity());
            orderItem.setOrder(newOrder);

            orderItemRepository.save(orderItem);
        }

        cartService.deleteCartItemsByUser(user);
    }
}
