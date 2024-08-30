package com.gdsc.OnlineLearningPlatform.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewDto {

    @NotNull
    @Min(value = 1, message = "Minimum value should be 1")
    @Max(value = 5, message = "Maximum value should be 5")
    private Integer rating;

    private String comment;
}
