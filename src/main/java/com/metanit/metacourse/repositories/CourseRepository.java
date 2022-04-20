package com.metanit.metacourse.repositories;

import com.metanit.metacourse.models.Course;
import com.metanit.metacourse.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository  extends JpaRepository<Course, Long> {
    Course findByTitle(String title);
    List<Course> findAll();

}
