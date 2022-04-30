package com.metanit.metacourse.services;

import com.metanit.metacourse.models.Course;
import com.metanit.metacourse.models.Module;
import com.metanit.metacourse.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CourseImplementation implements CourseService {
    private final CourseRepository courseRepository;


    @Override
    public Course saveCourse(Course course) {
        log.info("Saving new Course {}", course.getTitle());
        return courseRepository.save(course);
    }

    @Override
    public Course getCourse(String title) {
        log.info("Get course by title {}", title);
        return courseRepository.findByTitle(title);
    }

    @Override
    public List<Course> getCourses() {
        log.info("Get all courses");
        return courseRepository.findAll();
    }

    @Override
    public void addModuleToCourse(Module module, Course course) {
        course.getModules().add(module);
    }

    @Override
    public Course getCourse(Long id) {
        log.info("Get course by id {}", id);
        Optional<Course> course = courseRepository.findById(id);
        return course.orElseGet(Course::new);
    }

    @Override
    public void deleteCourse(Long id) {
        log.info("Delete course by id {}", id);
        Optional<Course> course = courseRepository.findById(id);
        try {
            courseRepository.delete(course.get());
        }
        catch (NoSuchElementException e){
            throw  new IllegalArgumentException(e);
        }
    }

    @Override
    public Course update(Long id, Course course) {
        log.info("Update course by id {} ", id);
        Course courseOld = getCourse(id);
        for (Module module : courseOld.getModules()) {
            course.getModules().add(module);
        }
        deleteCourse(id);
        saveCourse(course);
        return course;
    }
}
