package com.gdsc.OnlineLearningPlatform.controller;

import com.gdsc.OnlineLearningPlatform.dto.CourseDto;
import com.gdsc.OnlineLearningPlatform.dto.InstructorDto;
import com.gdsc.OnlineLearningPlatform.dto.ReviewDto;
import com.gdsc.OnlineLearningPlatform.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/online-learning")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public ResponseEntity<?> browseCourses(){
        return studentService.browseCourses();
    }

    @GetMapping("/courses/{studId}")
    public ResponseEntity<?> getMyCourses(@PathVariable Long studId){
        return studentService.getMyCourses(studId);
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<?> getCourse(@PathVariable Long courseId){
        return studentService.getCourse(courseId);
    }

    @PostMapping("enroll/{studId}/{courseId}")
    public ResponseEntity<String> enrollInCourse(@PathVariable Long studId, @PathVariable Long courseId){
        return studentService.enrollInCourse(studId, courseId);
    }

    @PostMapping("/review/{studId}/{courseId}")
    public ResponseEntity<String> leaveReview(@PathVariable Long studId, @PathVariable Long courseId
            , @Valid @RequestBody ReviewDto reviewDto){

        return studentService.leaveReview(studId, courseId, reviewDto);

    }

    @GetMapping("/courses/search")
    public ResponseEntity<List<CourseDto>> searchCourses(@RequestParam String keyword){
        return studentService.searchCourses(keyword);
    }

    @GetMapping("/instructors/search")
    public ResponseEntity<List<InstructorDto>> searchInstructor(@RequestParam String keyword){
        return studentService.searchInstructor(keyword);
    }
}
