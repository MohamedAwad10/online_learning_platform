package com.gdsc.OnlineLearningPlatform.mapper;

import com.gdsc.OnlineLearningPlatform.dto.CourseSubmissionDto;
import com.gdsc.OnlineLearningPlatform.model.CourseSubmission;
import org.springframework.stereotype.Component;

@Component
public class CourseSubmissionMapper {

    public CourseSubmissionDto toCourseSubmissionDto(CourseSubmission courseSubmission){
        return CourseSubmissionDto.builder()
                .title(courseSubmission.getTitle())
                .description(courseSubmission.getDescription())
                .status(courseSubmission.getStatus().toString())
                .submittedAt(courseSubmission.getSubmittedAt())
                .feedback(courseSubmission.getFeedback())
                .build();
    }
}
