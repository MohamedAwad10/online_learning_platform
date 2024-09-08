package com.gdsc.OnlineLearningPlatform.dto;

import com.gdsc.OnlineLearningPlatform.enums.CourseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Builder
public class CourseSubmissionDto {

    private String status;
    private LocalDate submittedAt;
    private String feedback;
    private String title;
    private String description;

}
