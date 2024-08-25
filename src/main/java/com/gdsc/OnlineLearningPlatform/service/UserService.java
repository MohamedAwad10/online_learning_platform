package com.gdsc.OnlineLearningPlatform.service;

import com.gdsc.OnlineLearningPlatform.dto.UserRegistrationDto;
import com.gdsc.OnlineLearningPlatform.model.Role;
import com.gdsc.OnlineLearningPlatform.model.User;
import com.gdsc.OnlineLearningPlatform.repository.RoleRepository;
import com.gdsc.OnlineLearningPlatform.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User registerUser(UserRegistrationDto userRegistration) throws Exception{

        if(userRepository.findByEmail(userRegistration.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email already in use");
        }

        Set<Role> roles = new HashSet<>();
        for(String roleName: userRegistration.getRoles()){

            Optional<Role> role = roleRepository.findByName(roleName.toUpperCase());
            if(role.isPresent()){
                roles.add(role.get());
            } else {
                throw new IllegalArgumentException("Role not found: "+roleName);
            }
        }

        User user = new User();
        user.setFirstName(userRegistration.getFirstName());
        user.setLastName(userRegistration.getLastName());
        user.setEmail(userRegistration.getEmail());
        user.setPassword(userRegistration.getPassword());
        user.setPhone(userRegistration.getPhone());
        user.setRoles(roles);

        return userRepository.save(user);
    }
}
