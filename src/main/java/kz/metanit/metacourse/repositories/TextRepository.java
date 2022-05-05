package kz.metanit.metacourse.repositories;

import kz.metanit.metacourse.models.Text;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TextRepository extends JpaRepository<Text, Long> {
    Text findByHeading(String heading);
    Optional<Text> findById(Long id);
    List<Text> findAll();
}
