package com.metanit.metacourse;

import com.metanit.metacourse.models.Person;
import com.metanit.metacourse.models.Role;
import com.metanit.metacourse.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class MetacourseApplication {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    public static void main(String[] args) {
        SpringApplication.run(MetacourseApplication.class, args);
    }
    @Bean
    public CommandLineRunner run(UserService userService){
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_TEACHER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveUser(new Person(null, "Bakhytzhan", "Amanzholov", "amanzholovbakhytzhan@gmail.com",
                    new Date(), "password23A", new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
            userService.addRoleToUser("amanzholovbakhytzhan@gmail.com", "ROLE_USER");
        };
    }
}
