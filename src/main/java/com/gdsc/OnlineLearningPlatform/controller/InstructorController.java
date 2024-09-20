package com.gdsc.OnlineLearningPlatform.controller;

import com.gdsc.OnlineLearningPlatform.dto.CourseDto;
import com.gdsc.OnlineLearningPlatform.model.Course;
import com.gdsc.OnlineLearningPlatform.service.InstructorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/online-learning/instructor")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping("/courses/{instructorId}")
    public ResponseEntity<?> myAllCourses(@PathVariable Long instructorId){
        return instructorService.myAllCourses(instructorId);
    }

    @GetMapping("/courses/{instructorId}/{courseId}")
    public ResponseEntity<?> getCourse(@PathVariable Long instructorId, @PathVariable Long courseId){
        return instructorService.getCourse(instructorId, courseId);
    }

    @GetMapping("/enrollments/{instructorId}/{courseId}")
    public ResponseEntity<?> getAllEnrollments(@PathVariable Long instructorId, @PathVariable Long courseId){
        return instructorService.getAllEnrollments(instructorId, courseId);
    }

    @PostMapping("/courses/{instructorId}")
    public ResponseEntity<String> createCourse(@PathVariable Long instructorId, @RequestBody CourseDto courseDto){
        return instructorService.submitCourseForApproval(instructorId, courseDto);
    }

    @PutMapping("/courses/{instructorId}/{courseId}")
    public ResponseEntity<String> updateCourse(@PathVariable Long instructorId, @PathVariable Long courseId
            , @Valid @RequestBody CourseDto courseDto){
        return instructorService.updateCourse(instructorId, courseId, courseDto);
    }

    @DeleteMapping("/courses/{instructorId}/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long instructorId, @PathVariable Long courseId){
        return instructorService.deleteCourse(instructorId, courseId);
    }

    @GetMapping("/courses/search/{instructorId}")
    public ResponseEntity<?> searchInstructorCourses(@PathVariable long instructorId, @RequestParam String keyword){
        return instructorService.searchInstructorCourses(instructorId, keyword);
    }
}
