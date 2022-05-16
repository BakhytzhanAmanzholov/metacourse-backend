package kz.metanit.metacourse;

import kz.metanit.metacourse.models.Category;
import kz.metanit.metacourse.models.Course;
import kz.metanit.metacourse.models.Person;
import kz.metanit.metacourse.models.Role;
import kz.metanit.metacourse.services.CategoryService;
import kz.metanit.metacourse.services.CourseService;
import kz.metanit.metacourse.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class MetacourseApplication {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(MetacourseApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(UserService userService, CategoryService categoryService, CourseService courseService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_TEACHER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveUser(new Person(null, "Bakhytzhan", "Amanzholov", "amanzholovbakhytzhan@gmail.com",
                    new Date(), "password23A",
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
            userService.addRoleToUser("amanzholovbakhytzhan@gmail.com", "ROLE_USER");
            userService.addRoleToUser("amanzholovbakhytzhan@gmail.com", "ROLE_ADMIN");

            Category category1 = new Category(null, "Programming");
            Category category2 = new Category(null, "Category");
            Category category3 = new Category(null, "Language");

            categoryService.saveCategory(category1);
            categoryService.saveCategory(category2);
            categoryService.saveCategory(category3);

            List<Category> categoryList1 = new ArrayList<>();
            categoryList1.add(category1);
            List<Category> categoryList2 = new ArrayList<>();
            categoryList2.add(category2);
            List<Category> categoryList3 = new ArrayList<>();
            categoryList3.add(category3);


            Course course1 = new Course(null, "Programming", "Programming", "4-5 hours",
                    categoryList1, 0.0, 0, new ArrayList<>());
            Course course2 = new Course(null, "Language", "Language", "4-5 hours",
                    categoryList3, 0.0, 0, new ArrayList<>());
            Course course3 = new Course(null, "Category", "Category", "4-5 hours",
                    categoryList2, 0.0, 0, new ArrayList<>());

            courseService.saveCourse(course1);
            courseService.saveCourse(course2);
            courseService.saveCourse(course3);

        };
    }
}
