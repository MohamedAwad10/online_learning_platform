package com.gdsc.OnlineLearningPlatform.mapper;

import com.gdsc.OnlineLearningPlatform.dto.CourseDto;
import com.gdsc.OnlineLearningPlatform.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    @Autowired
    private ReviewMapper reviewMapper;

    public CourseDto toCourseDto(Course course){

        return CourseDto.builder()
                .title(course.getTitle())
                .description(course.getDescription())
                .category(course.getCategory().getName())
                .tags(Collections.singleton(course.getTags()))
                .instructor(course.getInstructor().getFirstName().concat(" "+course.getInstructor().getLastName()))
                .numberOfInstructorCourses(course.getInstructor().getCourses().size())
                .updatedAt(course.getUpdatedAt())
                .numberOfReviews(course.getReviews().size())
                .reviews(course.getReviews().stream().map(reviewMapper::toReviewDto).collect(Collectors.toSet()))
                .numberOfEnrollments(course.getEnrollments().size())
                .instructorBio(course.getInstructor().getBio())
                .build();
    }
}
