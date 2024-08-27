package com.gdsc.OnlineLearningPlatform.repository;

import com.gdsc.OnlineLearningPlatform.model.CourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CourseCategory, Long> {

    Optional<CourseCategory> findByName(String name);
}
