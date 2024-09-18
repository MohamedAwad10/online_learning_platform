package com.gdsc.OnlineLearningPlatform.mapper;

import com.gdsc.OnlineLearningPlatform.dto.InstructorDto;
import com.gdsc.OnlineLearningPlatform.model.Instructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class InstructorMapper {

    private CourseMapper courseMapper;

    public InstructorMapper(CourseMapper courseMapper){
        this.courseMapper = courseMapper;
    }

    public InstructorDto toInstructorDto(Instructor instructor){

        long totalEnrolledStudents = instructor.getCourses()
                .stream()
                .mapToLong(course -> course.getEnrollments().size())
                .sum();

        long totalReviews = instructor.getCourses()
                .stream()
                .mapToLong(course -> course.getReviews().size())
                .sum();

        return InstructorDto.builder()
                .firstName(instructor.getFirstName())
                .lastName(instructor.getLastName())
                .bio(instructor.getBio())
                .yearsOfExperience(instructor.getYearsOfExperience())
                .totalStudents(totalEnrolledStudents)
                .reviews(totalReviews)
                .courses(instructor.getCourses().stream().map(courseMapper::toCourseDto).collect(Collectors.toSet()))
                .numOfCourses(instructor.getCourses().size())
                .build();
    }
}
