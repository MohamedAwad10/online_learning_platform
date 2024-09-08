package com.gdsc.OnlineLearningPlatform.mapper;

import com.gdsc.OnlineLearningPlatform.dto.UserDto;
import com.gdsc.OnlineLearningPlatform.dto.UserRegistrationDto;
import com.gdsc.OnlineLearningPlatform.model.Role;
import com.gdsc.OnlineLearningPlatform.model.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto toUserDto(User user){

//        Set<String> roles = user.getRoles().stream()
//                .map(Role::getName)
//                .map(String::toUpperCase)
//                .collect(Collectors.toSet());

        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRoles().stream().map(Role::getName).collect(Collectors.joining(", ")))
                .build();
    }

    public User toUserEntity(UserDto userDto){
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .build();
    }
}
