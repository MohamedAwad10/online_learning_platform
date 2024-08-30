package com.gdsc.OnlineLearningPlatform.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class UserRegistrationDto {

    @NotNull
    @NotBlank(message = "First Name is required")
    private String firstName;

    @NotNull
    @NotBlank(message = "Last Name is required")
    private String lastName;

    @NotNull
    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @NotNull
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 32, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{6,20})"
    , message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
    private String password;

    private String phone;

    @NotNull(message = "Roles are required")
    @NotEmpty
    private Set<@NotBlank(message = "Role cannot be blank") String> roles;

    private String birthDate;

    private String bio;

    private Integer yearsOfExperience;
}
