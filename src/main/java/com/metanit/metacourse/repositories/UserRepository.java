package com.metanit.metacourse.repositories;

import com.metanit.metacourse.models.Course;
import com.metanit.metacourse.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Person, Long> {
    Person findByEmail(String email);
    Optional<Person> findById(Long id);
}
