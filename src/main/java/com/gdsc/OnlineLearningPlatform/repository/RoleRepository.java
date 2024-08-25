package com.gdsc.OnlineLearningPlatform.repository;

import com.gdsc.OnlineLearningPlatform.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
