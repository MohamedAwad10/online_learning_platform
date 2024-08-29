package com.gdsc.OnlineLearningPlatform.repository;

import com.gdsc.OnlineLearningPlatform.model.CourseSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseSubmissionRepository extends JpaRepository<CourseSubmission, Long> {
}
