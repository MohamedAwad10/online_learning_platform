package com.gdsc.OnlineLearningPlatform.repository;

import com.gdsc.OnlineLearningPlatform.model.Course;
import com.gdsc.OnlineLearningPlatform.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    @Query("SELECT courses FROM Instructor WHERE id = :instructorId")
    Optional<Set<Course>> findCoursesByInstructorId(Long instructorId);
}
