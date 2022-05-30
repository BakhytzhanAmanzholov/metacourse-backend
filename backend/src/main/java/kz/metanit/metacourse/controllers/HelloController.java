package kz.metanit.metacourse.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class HelloController {
    @RequestMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> index() {
        return Collections.singletonMap("message", "Greetings from Spring Boot!");
    }
}
