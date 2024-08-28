package com.gdsc.OnlineLearningPlatform.controller;

import com.gdsc.OnlineLearningPlatform.dto.CourseDto;
import com.gdsc.OnlineLearningPlatform.repository.CourseRepository;
import com.gdsc.OnlineLearningPlatform.service.CourseService;
import com.gdsc.OnlineLearningPlatform.service.InstructorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping("/{instructorId}/courses")
    public ResponseEntity<?> myAllCourses(@PathVariable Long instructorId){
        return instructorService.myAllCourses(instructorId);
    }

    @PostMapping("/{instructorId}/course")
    public ResponseEntity<?> createCourse(@PathVariable Long instructorId, @RequestBody CourseDto courseDto){

        return instructorService.createCourse(instructorId, courseDto);
    }
}
