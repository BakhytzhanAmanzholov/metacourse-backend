package com.metanit.metacourse.controllers;

import com.metanit.metacourse.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping("/api/users")
    public ResponseEntity<?> index() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }
}
