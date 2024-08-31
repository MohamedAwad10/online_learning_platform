package com.gdsc.OnlineLearningPlatform.controller;

import com.gdsc.OnlineLearningPlatform.dto.AdminDto;
import com.gdsc.OnlineLearningPlatform.model.Course;
import com.gdsc.OnlineLearningPlatform.model.CourseSubmission;
import com.gdsc.OnlineLearningPlatform.model.User;
import com.gdsc.OnlineLearningPlatform.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/online-learning/admin")
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/addRole/{userId}")
    public ResponseEntity<String> addRoleToUser(@PathVariable Long userId, @Valid @RequestBody AdminDto adminDto){
        return adminService.addRoleToUser(userId, adminDto);
    }

    @PutMapping("/removeRole/{userId}")
    public ResponseEntity<String> removeRoleFromUser(@PathVariable Long userId, @Valid @RequestBody AdminDto adminDto){
        return adminService.removeRoleFromUser(userId, adminDto);
    }

    @GetMapping("/submissions")
    public ResponseEntity<List<CourseSubmission>> getAllSubmissions(){
        return adminService.getAllSubmissions();
    }

    @GetMapping("/courses")
    public ResponseEntity<?> getAllCourses(){
        return adminService.getAllCourses();
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable Long courseId){
        return adminService.getCourseById(courseId);
    }

    @GetMapping("/course/{title}")
    public ResponseEntity<?> getCourseByTitle(@PathVariable String title){
        return adminService.getCourseByTitle(title);
    }

    @DeleteMapping("/course/{courseId}")
    public ResponseEntity<String> deleteCourseById(@PathVariable Long courseId){
        return adminService.deleteCourseById(courseId);
    }

    @PutMapping("/approve/{submissionId}")
    public ResponseEntity<String> approveCourse(@PathVariable Long submissionId){
        return adminService.approveCourse(submissionId);
    }

    @PutMapping("/reject/{submissionId}")
    public ResponseEntity<String> rejectCourse(@PathVariable Long submissionId){
        return adminService.rejectCourse(submissionId);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        return adminService.getAllUsers();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId){
        return adminService.getUserById(userId);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId){
        return adminService.deleteUserById(userId);
    }
}
