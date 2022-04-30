package com.metanit.metacourse.controllers;

import com.metanit.metacourse.dto.CourseDto;
import com.metanit.metacourse.models.Course;
import com.metanit.metacourse.models.Person;
import com.metanit.metacourse.services.CourseService;
import com.metanit.metacourse.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
@Slf4j
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final UserService userService;

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
        Person user = userService.getUser(id);
        userService.addCourseToUser(course, user);
        return new ResponseEntity<>("Course created successfully", HttpStatus.OK);
    }


}
