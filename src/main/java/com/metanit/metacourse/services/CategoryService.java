package com.metanit.metacourse.services;

import com.metanit.metacourse.models.Category;

import java.util.List;

public interface CategoryService {
    Category saveCategory(Category category);
    void delete(Long id);
    List<Category> getCategories();
    Category getCategory(Long id);
    Category update(Long id, Category category);
    List<Category> findByNames(List<String> names);
    Category findByName(String name);
}
