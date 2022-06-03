package kz.metanit.metacourse.services;

import kz.metanit.metacourse.models.Course;
import kz.metanit.metacourse.models.Person;
import kz.metanit.metacourse.models.Role;

import java.util.List;

public interface UserService {
    Person saveUser(Person user);
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
    Person getUser(String email);
    List<Person> getUsers();
    Person getUser(Long id);
    void deleteUser(Long id);
    Person update(Long id, Person person);
    void addCourseCreateToUser(Course course, Person person);
    void addCourseTakenToUser(Course course, Person person);
    void completeCourse(Course course, Person person);
}
