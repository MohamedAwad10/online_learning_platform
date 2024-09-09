package com.gdsc.OnlineLearningPlatform.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class CategoryDto {

    private String category;

//    private Set<CourseDto> courses;
}
