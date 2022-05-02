package com.metanit.metacourse.repositories;

import com.metanit.metacourse.models.Category;
import com.metanit.metacourse.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByTitle(String title);

    Optional<Course> findById(Long id);

    List<Course> findAllByCategoriesIn(List<Category> categories);


}