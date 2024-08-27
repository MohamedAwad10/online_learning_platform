package com.gdsc.OnlineLearningPlatform.service;

import com.gdsc.OnlineLearningPlatform.dto.AdminDto;
import com.gdsc.OnlineLearningPlatform.model.Role;
import com.gdsc.OnlineLearningPlatform.model.User;
import com.gdsc.OnlineLearningPlatform.repository.RoleRepository;
import com.gdsc.OnlineLearningPlatform.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AdminService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    public AdminService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

//    public void checkUserAndRoles(Long userId, AdminDto adminDto){
//
//    }

    public ResponseEntity<String> addRoleToUser(Long userId, AdminDto adminDto) {

        if(adminDto.getRolesNames() == null || adminDto.getRolesNames().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No roles provided to add");
        }

        Optional<User> optionalUser =  userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();
        Set<Role> userRoles = user.getRoles();

        for(String roleName: adminDto.getRolesNames()){
            Optional<Role> optionalRole = roleRepository.findByName(roleName.toUpperCase());
            if(optionalRole.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found: "+roleName);
            }

            Role role = optionalRole.get();
            if(userRoles.contains(role)){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User already have this role: "+roleName);
            } else {
                userRoles.add(role);
            }
        }

        user.setRoles(userRoles);
        userRepository.save(user);
        return ResponseEntity.ok("Roles added to user successfully");
    }

    public ResponseEntity<String> removeRoleFromUser(Long userId, AdminDto adminDto) {

        if(adminDto.getRolesNames() == null || adminDto.getRolesNames().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No roles provided to remove");
        }

        Optional<User> optionalUser =  userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();
        Set<Role> userRoles = user.getRoles();

        for(String roleName: adminDto.getRolesNames()){
            Optional<Role> optionalRole = roleRepository.findByName(roleName.toUpperCase());
            if(optionalRole.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found: "+roleName);
            }

            Role role = optionalRole.get();
            if(userRoles.contains(role)) {
                userRoles.remove(role);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not have the role: "+roleName);
            }
        }

        user.setRoles(userRoles);
        userRepository.save(user);
        return ResponseEntity.ok("Role removed successfully");
    }
}
