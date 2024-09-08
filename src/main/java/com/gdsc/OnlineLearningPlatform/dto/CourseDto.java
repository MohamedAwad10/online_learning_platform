package com.gdsc.OnlineLearningPlatform.dto;

import com.gdsc.OnlineLearningPlatform.model.Review;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@Builder
public class CourseDto {

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @NotBlank
    private String category;

    private LocalDate updatedAt;

    private Set<String> tags;

    private String instructor;

    private String instructorBio;

    private int numberOfInstructorCourses;

    private int numberOfEnrollments;

    private Set<ReviewDto> reviews;

    private int numberOfReviews;
}
