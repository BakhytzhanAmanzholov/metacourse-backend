package kz.metanit.metacourse.controllers;

import kz.metanit.metacourse.dto.CategoryDto;
import kz.metanit.metacourse.models.Category;
import kz.metanit.metacourse.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@Slf4j
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/list")
    public ResponseEntity<?> index(){
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto){
        Category category = new Category();
        category.setName(categoryDto.getName());
        categoryService.saveCategory(category);
        return new ResponseEntity<>("Category created successfully", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.getCategory(id), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Category> getCategoryByName(@RequestBody CategoryDto categoryDto){
        Category category = categoryService.findByName(categoryDto.getName());
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
