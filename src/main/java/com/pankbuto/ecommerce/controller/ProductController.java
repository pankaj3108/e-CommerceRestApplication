package com.pankbuto.ecommerce.controller;

import com.pankbuto.ecommerce.dto.product.ProductDto;
import com.pankbuto.ecommerce.models.Category;
import com.pankbuto.ecommerce.service.CategoryService;
import com.pankbuto.ecommerce.service.ProductService;
import com.pankbuto.ecommerce.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> findAllProducts() {
        List<ProductDto> allProductDto = this.productService.findListOfProducts();
        return new ResponseEntity<List<ProductDto>>(allProductDto, HttpStatus.OK);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDto productDto) {
        Optional<Category> optionalCategory = categoryService.findCategory(productDto.getCategoryId());

        if(!optionalCategory.isPresent()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
        }

        Category category = optionalCategory.get();

        productService.addProduct(productDto, category);

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Integer productId, @Valid @RequestBody ProductDto productDto) {
        Optional<Category> optionalCategory = categoryService.findCategory(productDto.getCategoryId());

        if(!optionalCategory.isPresent()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
        }

        Category category = optionalCategory.get();

        productService.updateProduct(productId, productDto, category);

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
    }
}
