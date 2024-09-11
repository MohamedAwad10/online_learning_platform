package com.gdsc.OnlineLearningPlatform.mapper;

import com.gdsc.OnlineLearningPlatform.dto.EnrollmentDto;
import com.gdsc.OnlineLearningPlatform.model.Enrollment;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentMapper {

    public EnrollmentDto toEnrollmentDto(Enrollment enrollment){
        return EnrollmentDto.builder()
                .completed(enrollment.getCompleted())
                .enrolledDate(enrollment.getEnrolledDate())
                .studentName(enrollment.getStudent().getFirstName()
                        + " "+ enrollment.getStudent().getLastName())
                .build();
    }
}
