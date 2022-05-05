package kz.metanit.metacourse.controllers;

import kz.metanit.metacourse.dto.LessonDto;
import kz.metanit.metacourse.dto.ModuleDto;
import kz.metanit.metacourse.models.Course;
import kz.metanit.metacourse.models.Lesson;
import kz.metanit.metacourse.models.Module;
import kz.metanit.metacourse.services.LessonService;
import kz.metanit.metacourse.services.ModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lesson")
@Slf4j
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;
    private final ModuleService moduleService;

    @PostMapping("/api/{id}")
    public ResponseEntity<?> createLesson(@PathVariable Long id) {
        Lesson lesson = new Lesson();
        lessonService.saveLesson(lesson);
        Module module = moduleService.getModule(id);
        moduleService.addLessonToModule(lesson, module);
        return new ResponseEntity<>("Lesson created successfully", HttpStatus.OK);
    }

    @PutMapping("/api/{id}")
    public ResponseEntity<?> editLesson(@PathVariable Long id, @RequestBody LessonDto lessonDto) {
        Lesson lesson = new Lesson();
        lesson.setTitle(lessonDto.getTitle());
        lesson.setDescription(lessonDto.getDescription());
        lessonService.update(id, lesson);
        return new ResponseEntity<>("Lesson edit successfully", HttpStatus.OK);
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<Lesson> getLesson(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.getLesson(id));
    }
}
