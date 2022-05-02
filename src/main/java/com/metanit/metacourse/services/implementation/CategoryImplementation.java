package com.metanit.metacourse.services.implementation;

import com.metanit.metacourse.models.Category;
import com.metanit.metacourse.repositories.CategoryRepository;
import com.metanit.metacourse.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryImplementation implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
        log.info("Save new category by name {}", category.getName());
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        log.info("Delete category by id {}", id);
        Category category = getCategory(id);
        categoryRepository.delete(category);
    }

    @Override
    public List<Category> getCategories() {
        log.info("Get all categories");
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategory(Long id) {
        log.info("Find category by id {}", id);
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElseGet(Category::new);
    }

    @Override
    public Category update(Long id, Category category) {
        log.info("Update category by id {}", id);
        Category categoryOld = getCategory(id);
        categoryOld.setName(category.getName());
        return categoryOld;
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> findByNames(List<String> names) {
        log.info("Get categories by names: \n {} ", names.stream().toList());
        List<Category> categories = new ArrayList<>();
        for (String name: names) {
            Category category = categoryRepository.findByName(name);
            categories.add(category);
        }
        return categories;
    }
}
