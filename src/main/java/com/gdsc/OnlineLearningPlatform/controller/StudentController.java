package com.gdsc.OnlineLearningPlatform.controller;

import com.gdsc.OnlineLearningPlatform.dto.ReviewDto;
import com.gdsc.OnlineLearningPlatform.model.Course;
import com.gdsc.OnlineLearningPlatform.repository.CourseRepository;
import com.gdsc.OnlineLearningPlatform.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> browseCourses(){
        return studentService.browseCourses();
    }

    @PostMapping("{studId}/enroll/{courseId}")
    public ResponseEntity<String> enrollInCourse(@PathVariable Long studId, @PathVariable Long courseId){
        return studentService.enrollInCourse(studId, courseId);
    }

    @PostMapping("/{studId}/review/{courseId}")
    public ResponseEntity<String> leaveReview(@PathVariable Long studId, @PathVariable Long courseId
            , @Valid @RequestBody ReviewDto reviewDto){

        return studentService.leaveReview(studId, courseId, reviewDto);

    }
}
