package com.tpe.controller;

import com.tpe.domain.Category;
import com.tpe.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categoryList = categoryService.getAllCategory();
        return new ResponseEntity<>(categoryList,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findCategory(@PathVariable Long id){
        Category category = categoryService.findCategoryById(id);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        Category savedCategory = categoryService.addCategory(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id,@RequestBody Category updatedCategory){
        Category category = categoryService.updateCategory(id, updatedCategory);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Deleted successfully!",HttpStatus.OK);
    }
}
