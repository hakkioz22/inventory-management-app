package com.tpe.service;

import com.tpe.domain.Category;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    //Get all categories
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    //Find a category
    public Category findCategoryById(Long id){
        return categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found with id: " + id));
    }
    //Create a new category
    public Category addCategory(Category category){
        return categoryRepository.save(category);
    }

    //Update an existing category
    public Category updateCategory(Long id,Category updatedCategory){
        return categoryRepository.findById(id).
                map(category -> {category.setName(updatedCategory.getName());
                return categoryRepository.save(category);
                }).orElseThrow(()-> new ResourceNotFoundException("Category not found with this id: " + id));
    }

    //Delete category
    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }
}
