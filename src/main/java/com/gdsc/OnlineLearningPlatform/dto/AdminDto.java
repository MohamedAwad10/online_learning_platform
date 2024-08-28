package com.gdsc.OnlineLearningPlatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class AdminDto {

    @NotNull
    @NotEmpty
    private Set<@NotBlank @NotNull String> rolesNames;
}
