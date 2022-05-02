package com.metanit.metacourse.controllers;

import com.metanit.metacourse.dto.CategoryListDto;
import com.metanit.metacourse.dto.CourseDto;
import com.metanit.metacourse.models.Category;
import com.metanit.metacourse.models.Course;
import com.metanit.metacourse.models.Person;
import com.metanit.metacourse.services.CategoryService;
import com.metanit.metacourse.services.CourseService;
import com.metanit.metacourse.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/course")
@Slf4j
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final UserService userService;
    private final CategoryService categoryService;

    @GetMapping("/api")
    public ResponseEntity<?> getAllCourses() {
        return new ResponseEntity<>(courseService.getCourses(), HttpStatus.OK);
    }

    @PostMapping("/api/{id}")
    public ResponseEntity<?> createCourse(@PathVariable Long id, @RequestBody CourseDto courseDto) {
        Course course = new Course();
        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());
        course.setDuration(courseDto.getDuration());

        courseService.saveCourse(course);

        for (String name : courseDto.getCategories()) {
            Category category = categoryService.findByName(name);
            courseService.addCategoryToCourse(category, course); // TODO: создавать новые категории
        }

        Person user = userService.getUser(id);
        userService.addCourseToUser(course, user);
        return new ResponseEntity<>("Course created successfully", HttpStatus.OK);
    }

    @GetMapping("/api/categories")
    public ResponseEntity<?> getAllCourseByCategory(@RequestBody CategoryListDto categoryListDto) {
        List<Category> categoryList = categoryService.findByNames(categoryListDto.getCategories());
        return new ResponseEntity<>(courseService.findAllByCategories(categoryList), HttpStatus.OK);
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourse(id));
    }
}
