package kz.metanit.metacourse.repositories;

import kz.metanit.metacourse.models.Course;
import kz.metanit.metacourse.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Person, Long> {
    Person findByEmail(String email);
    Optional<Person> findById(Long id);
}
