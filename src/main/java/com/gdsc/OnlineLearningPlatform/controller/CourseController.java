package com.gdsc.OnlineLearningPlatform.controller;

import com.gdsc.OnlineLearningPlatform.dto.CourseDto;
import com.gdsc.OnlineLearningPlatform.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/course")
    public ResponseEntity<?> addCourse(@Valid @RequestBody CourseDto courseDto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(courseService.addCourse(courseDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
