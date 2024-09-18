package com.gdsc.OnlineLearningPlatform.service;

import com.gdsc.OnlineLearningPlatform.dto.CourseDto;
import com.gdsc.OnlineLearningPlatform.dto.InstructorDto;
import com.gdsc.OnlineLearningPlatform.dto.ReviewDto;
import com.gdsc.OnlineLearningPlatform.mapper.CourseMapper;
import com.gdsc.OnlineLearningPlatform.mapper.InstructorMapper;
import com.gdsc.OnlineLearningPlatform.model.*;
import com.gdsc.OnlineLearningPlatform.repository.CourseRepository;
import com.gdsc.OnlineLearningPlatform.repository.EnrollmentRepository;
import com.gdsc.OnlineLearningPlatform.repository.ReviewRepository;
import com.gdsc.OnlineLearningPlatform.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final CourseRepository courseRepository;

    private final StudentRepository studentRepository;

    private final EnrollmentRepository enrollmentRepository;

    private final ReviewRepository reviewRepository;

    private CourseMapper courseMapper;

    private InstructorMapper instructorMapper;

    public StudentService(CourseRepository courseRepository, StudentRepository studentRepository
            , EnrollmentRepository enrollmentRepository, ReviewRepository reviewRepository
            , CourseMapper courseMapper, InstructorMapper instructorMapper) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.reviewRepository = reviewRepository;
        this.courseMapper = courseMapper;
        this.instructorMapper = instructorMapper;
    }

    public ResponseEntity<?> browseCourses(){
        List<Course> courses = courseRepository.findAll();
        if(courses.isEmpty()){
            return ResponseEntity.ok().body("There are no Courses");
        }

        List<CourseDto> allCourses = courses.stream().map(course -> courseMapper.toCourseDto(course)).toList();

        return ResponseEntity.ok().body(allCourses);
    }

    public ResponseEntity<?> getMyCourses(Long studId) {
        Optional<Student> optionalStudent = studentRepository.findById(studId);
        if(optionalStudent.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }

        Student student = optionalStudent.get();
        Set<Course> studentCourses =  student.getCourses();

        Set<CourseDto> allStudentCourses = studentCourses.stream().map(course -> courseMapper.toCourseDto(course)).collect(Collectors.toSet());

        return ResponseEntity.ok(allStudentCourses);
    }

    public ResponseEntity<?> getCourse(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalCourse.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }

        Course course = optionalCourse.get();

        return ResponseEntity.ok().body(courseMapper.toCourseDto(course));
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

        student.getCourses().add(course);
        course.getStudents().add(student);

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

        if(student.getReviews().stream().anyMatch(review -> review.getCourse().equals(course))){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Student already reviewed this course");
        }

        Review review = new Review();
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setStudent(student);
        review.setCourse(course);

        reviewRepository.save(review);
        return ResponseEntity.ok("Review submitted successfully");
    }

    public ResponseEntity<List<CourseDto>> searchCourses(String keyword) {
        List<Course> courses = studentRepository.searchCourses(keyword);
        List<CourseDto> allCourses = courses.stream().map(courseMapper::toCourseDto).toList();
        return ResponseEntity.ok(allCourses);
    }

    public ResponseEntity<List<InstructorDto>> searchInstructor(String keyword) {

        List<Instructor> instructors = studentRepository.searchInstructor(keyword);
        List<InstructorDto> allInstructors = instructors.stream().map(instructorMapper::toInstructorDto).toList();
        return ResponseEntity.ok(allInstructors);
    }
}
