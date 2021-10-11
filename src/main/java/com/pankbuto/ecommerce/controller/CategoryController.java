package com.pankbuto.ecommerce.controller;

import com.pankbuto.ecommerce.models.Category;
import com.pankbuto.ecommerce.service.CategoryService;
import com.pankbuto.ecommerce.utils.ApiResponse;
import com.pankbuto.ecommerce.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<Category>> findAllCategories() {
        List<Category> categories = this.categoryService.findAllCategories();

        return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
    }

    @PostMapping("/addCategory")
    public ResponseEntity<ApiResponse> addCategory(@Valid @RequestBody Category category) {
        if(Helper.notNull(categoryService.findCategory(category.getCategoryName()))) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Category already exists"), HttpStatus.CONFLICT);
        }

        this.categoryService.addCategory(category);

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Category successfully added"), HttpStatus.OK);
    }

}
