package com.pankbuto.ecommerce.controller;

import com.pankbuto.ecommerce.dto.cart.AddToCartDto;
import com.pankbuto.ecommerce.dto.cart.CartDto;
import com.pankbuto.ecommerce.models.Product;
import com.pankbuto.ecommerce.models.User;
import com.pankbuto.ecommerce.service.AuthenticationService;
import com.pankbuto.ecommerce.service.CartService;
import com.pankbuto.ecommerce.service.ProductService;
import com.pankbuto.ecommerce.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto, @RequestParam("token") String token) throws Exception {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        Product product = productService.getProductById(addToCartDto.getProductId());

        this.cartService.addToCart(addToCartDto, user, product);

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "product has been added to cart"), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<CartDto>getCartItems(@RequestParam("token") String token) throws Exception {
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        CartDto cartDto = this.cartService.findAllCartItems(user);

        return new ResponseEntity<CartDto>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Integer cartItemId, @RequestParam("token") String token) throws Exception {

        authenticationService.authenticate(token);

        Integer userId = authenticationService.getUser(token).getId();

        this.cartService.deleteCartItem(cartItemId, userId);

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "cart Item has been removed"), HttpStatus.OK);
    }

}
