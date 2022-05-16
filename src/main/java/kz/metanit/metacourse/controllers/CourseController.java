package kz.metanit.metacourse.controllers;

import kz.metanit.metacourse.dto.CategoryListDto;
import kz.metanit.metacourse.dto.CourseDto;
import kz.metanit.metacourse.models.Category;
import kz.metanit.metacourse.models.Course;
import kz.metanit.metacourse.models.Person;
import kz.metanit.metacourse.services.CategoryService;
import kz.metanit.metacourse.services.CourseService;
import kz.metanit.metacourse.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/api")
    public ResponseEntity<?> createCourse(@RequestBody CourseDto courseDto) {
        Course course = new Course();
        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());
        course.setDuration(courseDto.getDuration());

        courseService.saveCourse(course);

        for (String name : courseDto.getCategories()) {
            Category category = categoryService.findByName(name);
            if(category == null){
                category = categoryService.saveCategory(new Category(name));
            }
            courseService.addCategoryToCourse(category, course);
        }
        Person user = userService.getUser(isLogged());
        userService.addCourseCreateToUser(course, user);
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


    @PostMapping("/api/{id}")
    public ResponseEntity<?> ratingCourse(@PathVariable Long id, @RequestBody int rating){
        Course course = courseService.getCourse(id);
        courseService.rating(course, rating);
        return new ResponseEntity<>("Rated", HttpStatus.OK);
    }

    public String isLogged(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        log.info(currentPrincipalName);
        if(!currentPrincipalName.equals("anonymousUser")){
            return currentPrincipalName;
        }
        return "anonymousUser";
    }
}
