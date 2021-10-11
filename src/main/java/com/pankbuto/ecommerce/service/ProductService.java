package com.pankbuto.ecommerce.service;

import com.pankbuto.ecommerce.dto.product.ProductDto;
import com.pankbuto.ecommerce.models.Category;
import com.pankbuto.ecommerce.models.Product;
import com.pankbuto.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDto> findListOfProducts() {
        List<Product> listOfProducts = this.productRepository.findAll();
        List<ProductDto> result = new ArrayList<>();
        for(Product product : listOfProducts) {
            ProductDto pd = new ProductDto(product);
            result.add(pd);
        }

        return result;
    }

    public void addProduct(ProductDto productDto, Category category) {
        Product product = new Product(productDto, category);
        this.productRepository.save(product);
    }

    public void updateProduct(Integer productId, ProductDto productDto, Category category) {
        Product product = new Product(productDto, category);
        product.setId(productId);
        this.productRepository.save(product);
    }
}
