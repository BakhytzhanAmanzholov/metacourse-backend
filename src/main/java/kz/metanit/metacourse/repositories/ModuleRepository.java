package kz.metanit.metacourse.repositories;

import kz.metanit.metacourse.models.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    Module findByTitle(String title);
    Optional<Module> findById(Long id);
    List<Module> findAll();
}
