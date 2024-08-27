package com.gdsc.OnlineLearningPlatform.controller;

import com.gdsc.OnlineLearningPlatform.dto.AdminDto;
import com.gdsc.OnlineLearningPlatform.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
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
}
