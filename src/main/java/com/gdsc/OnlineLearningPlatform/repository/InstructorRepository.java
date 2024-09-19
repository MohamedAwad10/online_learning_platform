package com.gdsc.OnlineLearningPlatform.repository;

import com.gdsc.OnlineLearningPlatform.model.Course;
import com.gdsc.OnlineLearningPlatform.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    @Query("SELECT courses FROM Instructor WHERE id = :instructorId")
    Optional<Set<Course>> findCoursesByInstructorId(Long instructorId);

    @Query("SELECT c FROM Instructor i JOIN i.courses c WHERE i.id = :instructorId AND " +
            "(LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.tags) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.category.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Set<Course> searchInstructorCourses(long instructorId, String keyword);
}
