package com.metanit.metacourse.controllers;

import com.metanit.metacourse.dto.ModuleDto;
import com.metanit.metacourse.models.Course;
import com.metanit.metacourse.models.Module;
import com.metanit.metacourse.services.CourseService;
import com.metanit.metacourse.services.ModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/module")
@Slf4j
@RequiredArgsConstructor
public class ModuleController {
    private final ModuleService moduleService;
    private final CourseService courseService;

    @PostMapping("/api/{id}")
    public ResponseEntity<?> createModule(@PathVariable Long id){
        Module module = new Module();
        moduleService.saveModule(module);
        Course course = courseService.getCourse(id);
        courseService.addModuleToCourse(module,course);
        return new ResponseEntity<>("Module created successfully", HttpStatus.OK);
    }

    @PutMapping("/api/{id}")
    public ResponseEntity<?> editModule(@PathVariable Long id, @RequestBody ModuleDto moduleDto){
        Module module = new Module();
        module.setTitle(moduleDto.getTitle());
        module.setDescription(moduleDto.getDuration());
        moduleService.update(id, module);
        return new ResponseEntity<>("Module edit successfully", HttpStatus.OK);
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<Module> getModule(@PathVariable Long id){
        return ResponseEntity.ok(moduleService.getModule(id));
    }
}
