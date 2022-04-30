package com.metanit.metacourse.services;

import com.metanit.metacourse.models.Lesson;
import com.metanit.metacourse.models.Text;

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
