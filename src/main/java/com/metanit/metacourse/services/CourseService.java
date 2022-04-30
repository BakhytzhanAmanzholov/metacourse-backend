package com.metanit.metacourse.services;

import com.metanit.metacourse.models.Course;
import com.metanit.metacourse.models.Module;

import java.util.List;

public interface CourseService {
    Course saveCourse(Course course);
    Course getCourse(String title);
    List<Course> getCourses();
    Course getCourse(Long id);
    void deleteCourse(Long id);
    Course update(Long id, Course course);
    void addModuleToCourse(Module module, Course course);
}
