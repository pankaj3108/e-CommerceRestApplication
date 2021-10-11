package com.pankbuto.ecommerce.service;

import com.pankbuto.ecommerce.models.Category;
import com.pankbuto.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public Optional<Category> findCategory(Integer categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public List<Category> findAllCategories() {
        return this.categoryRepository.findAll();
    }

    public Category findCategory(String categoryName) {
        return this.categoryRepository.findByCategoryName(categoryName);
    }

    public void addCategory(Category category) {
        this.categoryRepository.save(category);
    }
}
