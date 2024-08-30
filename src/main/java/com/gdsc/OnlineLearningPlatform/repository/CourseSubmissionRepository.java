package com.gdsc.OnlineLearningPlatform.repository;

import com.gdsc.OnlineLearningPlatform.model.CourseSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseSubmissionRepository extends JpaRepository<CourseSubmission, Long> {
    Optional<CourseSubmission> findByTitle(String title);
}
