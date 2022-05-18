package kz.metanit.metacourse.services.implementation;

import kz.metanit.metacourse.models.Lesson;
import kz.metanit.metacourse.models.Module;
import kz.metanit.metacourse.models.Text;
import kz.metanit.metacourse.repositories.LessonRepository;
import kz.metanit.metacourse.repositories.TextRepository;
import kz.metanit.metacourse.services.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LessonImplementation implements LessonService {
    private final LessonRepository lessonRepository;
    private final TextRepository textRepository;

    @Override
    public Lesson saveLesson(Lesson lesson) {
        log.info("Saving new lesson by name {}", lesson.getTitle());
        return lessonRepository.save(lesson);
    }


    @Override
    public void addTextToLesson(Text text, Lesson lesson) {
        log.info("Adding text {} to lesson {}", text.getText(), lesson.getTitle());
        lesson.getTexts().add(text);
    }

    @Override
    public Lesson getLesson(String title) {
        log.info("Get lesson by title {}", title);
        return lessonRepository.findByTitle(title);
    }

    @Override
    public List<Lesson> getLessons() {
        log.info("Get lessons");
        return lessonRepository.findAll();
    }

    @Override
    public Lesson getLesson(Long id) {
        log.info("Get lesson by id {}", id);
        Optional<Lesson> lesson = lessonRepository.findById(id);
        return lesson.orElseGet(Lesson::new);
    }

    @Override
    public void deleteLesson(Long id) {
        Lesson lesson = getLesson(id);
        lessonRepository.delete(lesson); // TODO: исправить все удаление 'проблема с зависимостью module-lesson'
    }

    @Override
    public Lesson update(Long id, Lesson lesson) {
        log.info("Update lesson by id {} ", id);
        Lesson lessonOld = getLesson(id);
        lessonOld.setTitle(lesson.getTitle());
        lessonOld.setDescription(lesson.getDescription());
        return lessonOld;
    }
}
