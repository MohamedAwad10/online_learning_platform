package com.gdsc.OnlineLearningPlatform.service;

import com.gdsc.OnlineLearningPlatform.dto.ReviewDto;
import com.gdsc.OnlineLearningPlatform.model.Course;
import com.gdsc.OnlineLearningPlatform.model.Enrollment;
import com.gdsc.OnlineLearningPlatform.model.Review;
import com.gdsc.OnlineLearningPlatform.model.Student;
import com.gdsc.OnlineLearningPlatform.repository.CourseRepository;
import com.gdsc.OnlineLearningPlatform.repository.EnrollmentRepository;
import com.gdsc.OnlineLearningPlatform.repository.ReviewRepository;
import com.gdsc.OnlineLearningPlatform.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final CourseRepository courseRepository;

    private final StudentRepository studentRepository;

    private final EnrollmentRepository enrollmentRepository;

    private final ReviewRepository reviewRepository;

    public StudentService(CourseRepository courseRepository, StudentRepository studentRepository
            , EnrollmentRepository enrollmentRepository, ReviewRepository reviewRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.reviewRepository = reviewRepository;
    }

    public ResponseEntity<List<Course>> browseCourses(){
        List<Course> courses = courseRepository.findAll();
        return ResponseEntity.ok().body(courses);
    }

    public ResponseEntity<String> enrollInCourse(Long studId, Long courseId) {

        Optional<Student> optionalStudent = studentRepository.findById(studId);
        if(optionalStudent.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }

        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalCourse.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }

        Student student = optionalStudent.get();
        Course course = optionalCourse.get();

        if(student.getEnrollments().stream().anyMatch(enrollment -> enrollment.getCourse().equals(course))){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Student already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        enrollmentRepository.save(enrollment);
        return ResponseEntity.ok().body("Student enrolled in course successfully");
    }

    public ResponseEntity<String> leaveReview(Long studId, Long courseId, ReviewDto reviewDto) {

        Optional<Student> optionalStudent = studentRepository.findById(studId);
        if(optionalStudent.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }

        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalCourse.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }

        Student student = optionalStudent.get();
        Course course = optionalCourse.get();

        Review review = new Review();
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setStudent(student);
        review.setCourse(course);

        reviewRepository.save(review);
        return ResponseEntity.ok("Review submitted successfully");
    }
}
