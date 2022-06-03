package kz.metanit.metacourse.controllers;

import kz.metanit.metacourse.services.CourseService;
import kz.metanit.metacourse.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final CourseService courseService;

    @GetMapping("/users")
    public ResponseEntity<?> listOfUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/courses")
    public ResponseEntity<?> listOfCourses() {
        return new ResponseEntity<>(courseService.getCourses(), HttpStatus.OK);
    }
}
