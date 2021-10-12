package com.pankbuto.ecommerce.service;

import com.pankbuto.ecommerce.dto.cart.AddToCartDto;
import com.pankbuto.ecommerce.dto.cart.CartDto;
import com.pankbuto.ecommerce.dto.cart.CartItemDto;
import com.pankbuto.ecommerce.models.Cart;
import com.pankbuto.ecommerce.models.Product;
import com.pankbuto.ecommerce.models.User;
import com.pankbuto.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public void addToCart(AddToCartDto addToCartDto, User user, Product product) {
        Cart cart = new Cart(product, addToCartDto.getQuantity(), user);

        this.cartRepository.save(cart);
    }

    public CartDto findAllCartItems(User user) {

        List<Cart> allCartItems = this.cartRepository.findAllByUserOrderByCreatedDateDesc(user);

        List<CartItemDto> cartItems = new ArrayList<>();

        for(Cart cart : allCartItems) {
            CartItemDto cartItemDto = new CartItemDto(cart);

            cartItems.add(cartItemDto);
        }

        double totalCost = 0;

        for(CartItemDto cartItemDto : cartItems) {
            totalCost += ((cartItemDto.getProduct().getPrice())*cartItemDto.getQuantity());
        }

        return new CartDto(cartItems, totalCost);

    }

    public void deleteCartItem(Integer cartItemId, Integer userId) throws Exception {
        if(!cartRepository.existsById(cartItemId)) {
            throw new Exception("cart Item Id is invalid");
        }

        cartRepository.deleteById(cartItemId);
    }

    public void deleteCartItemsByUser(User user) {
        cartRepository.deleteByUser(user);
    }
}
