package kz.metanit.metacourse.controllers;

import kz.metanit.metacourse.dto.LessonDto;
import kz.metanit.metacourse.dto.TextDto;
import kz.metanit.metacourse.models.Lesson;
import kz.metanit.metacourse.models.Module;
import kz.metanit.metacourse.models.Text;
import kz.metanit.metacourse.services.LessonService;
import kz.metanit.metacourse.services.TextService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/text")
@Slf4j
@RequiredArgsConstructor
public class TextController {
    private final TextService textService;
    private final LessonService lessonService;

    @PostMapping("/{id}")
    public ResponseEntity<?> createText(@PathVariable Long id) {
        Text text = new Text();
        textService.saveText(text);
        Lesson lesson = lessonService.getLesson(id);
        lessonService.addTextToLesson(text, lesson);
        return new ResponseEntity<>("Text created successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editText(@PathVariable Long id, @RequestBody TextDto textDto) {
        Text text = new Text();
        text.setText(textDto.getText());
        text.setHeading(textDto.getHeading());
        textService.update(id, text);
        return new ResponseEntity<>("Text edit successfully", HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteText(@PathVariable Long id){
        textService.deleteText(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
