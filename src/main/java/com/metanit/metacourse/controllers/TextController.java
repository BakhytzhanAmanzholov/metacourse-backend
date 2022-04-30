package com.metanit.metacourse.controllers;

import com.metanit.metacourse.dto.LessonDto;
import com.metanit.metacourse.dto.TextDto;
import com.metanit.metacourse.models.Lesson;
import com.metanit.metacourse.models.Module;
import com.metanit.metacourse.models.Text;
import com.metanit.metacourse.services.LessonService;
import com.metanit.metacourse.services.TextService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/text")
@Slf4j
@RequiredArgsConstructor
public class TextController {
    private final TextService textService;
    private final LessonService lessonService;

    @PostMapping("/api/{id}")
    public ResponseEntity<?> createText(@PathVariable Long id) {
        Text text = new Text();
        textService.saveText(text);
        Lesson lesson = lessonService.getLesson(id);
        lessonService.addTextToLesson(text, lesson);
        return new ResponseEntity<>("Text created successfully", HttpStatus.OK);
    }

    @PutMapping("/api/{id}")
    public ResponseEntity<?> editText(@PathVariable Long id, @RequestBody TextDto textDto) {
        Text text = new Text();
        text.setText(textDto.getText());
        text.setHeading(textDto.getHeading());
        textService.update(id, text);
        return new ResponseEntity<>("Text edit successfully", HttpStatus.OK);
    }
}
