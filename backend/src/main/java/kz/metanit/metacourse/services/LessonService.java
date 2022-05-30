package kz.metanit.metacourse.services;

import kz.metanit.metacourse.models.Lesson;
import kz.metanit.metacourse.models.Text;

import java.util.List;

public interface LessonService {
    Lesson saveLesson(Lesson lesson);
    void addTextToLesson(Text text, Lesson lesson);
    Lesson getLesson(String title);
    List<Lesson> getLessons();
    Lesson getLesson(Long id);
    void deleteLesson(Long id);
    Lesson update(Long id, Lesson lesson);
}
