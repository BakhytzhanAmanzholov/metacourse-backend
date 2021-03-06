package kz.metanit.metacourse.repositories;

import kz.metanit.metacourse.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    Optional<Category> findById(Long id);

    List<Category> findAll();
}
