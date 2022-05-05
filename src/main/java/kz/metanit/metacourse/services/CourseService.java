package kz.metanit.metacourse.services;

import kz.metanit.metacourse.models.Category;
import kz.metanit.metacourse.models.Course;
import kz.metanit.metacourse.models.Module;

import java.util.List;

public interface CourseService {
    Course saveCourse(Course course);
    Course getCourse(String title);
    List<Course> getCourses();
    Course getCourse(Long id);
    void deleteCourse(Long id);
    Course update(Long id, Course course);
    void addModuleToCourse(Module module, Course course);
    void addCategoryToCourse(Category category, Course course);

    List<Course> findAllByCategories(List<Category> categories);
}
