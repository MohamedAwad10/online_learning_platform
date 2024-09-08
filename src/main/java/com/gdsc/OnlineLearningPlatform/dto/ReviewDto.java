package com.gdsc.OnlineLearningPlatform.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Builder
public class ReviewDto {

    private String studentName;

    @NotNull
    @Min(value = 1, message = "Minimum value should be 1")
    @Max(value = 5, message = "Maximum value should be 5")
    private Double rating;

    private String comment;

    private LocalDate createdAt;
}
