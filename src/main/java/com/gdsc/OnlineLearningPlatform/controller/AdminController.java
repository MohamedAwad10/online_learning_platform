package com.gdsc.OnlineLearningPlatform.controller;

import com.gdsc.OnlineLearningPlatform.dto.AdminDto;
import com.gdsc.OnlineLearningPlatform.dto.CourseSubmissionDto;
import com.gdsc.OnlineLearningPlatform.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/online-learning/admins")
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
    public ResponseEntity<List<CourseSubmissionDto>> getAllSubmissions(){
        return adminService.getAllSubmissions();
    }

    @GetMapping("/courses")
    public ResponseEntity<?> getAllCourses(){
        return adminService.getAllCourses();
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable Long courseId){
        return adminService.getCourseById(courseId);
    }

    @GetMapping("/courses/{title}")
    public ResponseEntity<?> getCourseByTitle(@PathVariable String title){
        return adminService.getCourseByTitle(title);
    }

    @DeleteMapping("/courses/{courseId}")
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

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId){
        return adminService.getUserById(userId);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId){
        return adminService.deleteUserById(userId);
    }
}
