package com.gdsc.OnlineLearningPlatform.dto;

import com.gdsc.OnlineLearningPlatform.model.Course;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Builder
public class InstructorDto {

    private String firstName;
    private String lastName;
    private String bio;
    private long totalStudents;
    private long reviews;
    private Integer yearsOfExperience;
    private Set<CourseDto> courses;
    private int numOfCourses;
}
