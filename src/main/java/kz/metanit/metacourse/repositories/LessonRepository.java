package kz.metanit.metacourse.repositories;

import kz.metanit.metacourse.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Lesson findByTitle(String title);
    Optional<Lesson> findById(Long id);
    List<Lesson> findAll();
}
