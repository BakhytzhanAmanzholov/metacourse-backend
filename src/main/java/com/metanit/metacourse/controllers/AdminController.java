package com.metanit.metacourse.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    @GetMapping("/me")
    public ResponseEntity<?> index(){
        log.info("hello");
        return new ResponseEntity<>("Email is already taken!", HttpStatus.OK);
    }
}
