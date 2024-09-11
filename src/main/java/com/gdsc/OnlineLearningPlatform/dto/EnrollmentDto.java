package com.gdsc.OnlineLearningPlatform.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Builder
public class EnrollmentDto {

    private String studentName;
    private Boolean completed;
    private LocalDate enrolledDate;
}
