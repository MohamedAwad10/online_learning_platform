package com.gdsc.OnlineLearningPlatform.service;

import com.gdsc.OnlineLearningPlatform.dto.UserLoginDto;
import com.gdsc.OnlineLearningPlatform.dto.UserRegistrationDto;
import com.gdsc.OnlineLearningPlatform.model.Instructor;
import com.gdsc.OnlineLearningPlatform.model.Role;
import com.gdsc.OnlineLearningPlatform.model.Student;
import com.gdsc.OnlineLearningPlatform.model.User;
import com.gdsc.OnlineLearningPlatform.repository.RoleRepository;
import com.gdsc.OnlineLearningPlatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public ResponseEntity<String> registerUser(UserRegistrationDto userRegistration){

        if(userRepository.findByEmail(userRegistration.getEmail()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }

        Set<Role> roles = new HashSet<>();
        for(String roleName: userRegistration.getRoles()){
            Optional<Role> role = roleRepository.findByName(roleName.toUpperCase());
            if(role.isPresent() && !roleName.equalsIgnoreCase("admin")){
                roles.add(role.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found: "+roleName);
            }
        }

        User user;
        if(roles.stream().anyMatch(role -> role.getName().equalsIgnoreCase("INSTRUCTOR"))){
            user = new Instructor();
            ((Instructor) user).setBio(userRegistration.getBio());
            ((Instructor) user).setYearsOfExperience(userRegistration.getYearsOfExperience());
        } else if(roles.stream().anyMatch(role -> role.getName().equalsIgnoreCase("STUDENT"))){
            user = new Student();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            ((Student) user).setBirthDate(LocalDate.parse(userRegistration.getBirthDate(), formatter));
        } else {
            user = new User();
        }
        user.setFirstName(userRegistration.getFirstName());
        user.setLastName(userRegistration.getLastName());
        user.setEmail(userRegistration.getEmail());
//        user.setPassword(userRegistration.getPassword());
        user.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
        user.setPhone(userRegistration.getPhone());
        user.setRoles(roles);

        userRepository.save(user);

        return ResponseEntity.ok("Successful register");
    }

    public ResponseEntity<String> loginUser(UserLoginDto userLoginDto) throws Exception{

        Optional<User> userLogin = userRepository.findByEmail(userLoginDto.getEmail());
        if(userLogin.isPresent()){
            User user = userLogin.get();
            if(passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword()))
                return ResponseEntity.ok("Logged in Successfully");
            else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password is wrong");
        } else {
            throw new IllegalArgumentException("This Email is not found, Please register first.");
        }
    }
}
