package com.gdsc.OnlineLearningPlatform.mapper;

import com.gdsc.OnlineLearningPlatform.dto.CourseDto;
import com.gdsc.OnlineLearningPlatform.dto.ReviewDto;
import com.gdsc.OnlineLearningPlatform.model.Course;
import com.gdsc.OnlineLearningPlatform.model.Review;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

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
                .reviews(course.getReviews().stream().map(this::toReviewDto).collect(Collectors.toSet()))
                .numberOfEnrollments(course.getEnrollments().size())
                .instructorBio(course.getInstructor().getBio())
                .build();
    }

    public ReviewDto toReviewDto(Review review){
        return ReviewDto.builder()
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .studentName(review.getStudent().getFirstName()+" "+review.getStudent().getLastName())
                .build();
    }
}
