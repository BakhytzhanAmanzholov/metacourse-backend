package com.metanit.metacourse.repositories;

import com.metanit.metacourse.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepositories extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
