package com.gdsc.OnlineLearningPlatform.service;

import com.gdsc.OnlineLearningPlatform.dto.CourseDto;
import com.gdsc.OnlineLearningPlatform.dto.EnrollmentDto;
import com.gdsc.OnlineLearningPlatform.enums.CourseStatus;
import com.gdsc.OnlineLearningPlatform.mapper.CourseMapper;
import com.gdsc.OnlineLearningPlatform.mapper.EnrollmentMapper;
import com.gdsc.OnlineLearningPlatform.model.*;
import com.gdsc.OnlineLearningPlatform.repository.CategoryRepository;
import com.gdsc.OnlineLearningPlatform.repository.CourseRepository;
import com.gdsc.OnlineLearningPlatform.repository.CourseSubmissionRepository;
import com.gdsc.OnlineLearningPlatform.repository.InstructorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;

    private final CategoryRepository categoryRepository;

    private final CourseRepository courseRepository;

    private final CourseSubmissionRepository courseSubmissionRepository;

    private final CourseMapper courseMapper;

    private final EnrollmentMapper enrollmentMapper;

    public InstructorService(InstructorRepository instructorRepository,
                             CategoryRepository categoryRepository, CourseRepository courseRepository
                                , CourseSubmissionRepository courseSubmissionRepository
                                , CourseMapper courseMapper, EnrollmentMapper enrollmentMapper) {
        this.instructorRepository = instructorRepository;
        this.categoryRepository = categoryRepository;
        this.courseRepository = courseRepository;
        this.courseSubmissionRepository = courseSubmissionRepository;
        this.courseMapper = courseMapper;
        this.enrollmentMapper = enrollmentMapper;
    }

    public ResponseEntity<?> myAllCourses(Long instructorId) {

        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        if(optionalInstructor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instructor not found");
        }

        Optional<Set<Course>> myCourses = instructorRepository.findCoursesByInstructorId(instructorId);

        if(myCourses.isPresent()){
            Set<Course> courses = myCourses.get();
            Set<CourseDto> allCourses = courses.stream()
                    .map(courseMapper::toCourseDto).collect(Collectors.toSet());
            return ResponseEntity.ok(allCourses);
        }
        return ResponseEntity.ok().body("There are no courses for instructor");
    }

    public ResponseEntity<?> getCourse(Long instructorId, Long courseId) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        if(optionalInstructor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instructor not found");
        }

        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalCourse.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }

        Instructor instructor = optionalInstructor.get();
        Course myCourse = optionalCourse.get();

        if(instructor.getCourses().stream().noneMatch(course -> course.equals(myCourse))){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Instructor does not have this course");
        }

        return ResponseEntity.ok().body(courseMapper.toCourseDto(myCourse));
    }

    public ResponseEntity<?> getAllEnrollments(Long instructorId, Long courseId) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        if(optionalInstructor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instructor not found");
        }

        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalCourse.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }

        Instructor instructor = optionalInstructor.get();
        Course myCourse = optionalCourse.get();
        if(instructor.getCourses().stream().anyMatch(course -> course.equals(myCourse))){
            Set<Enrollment> enrollments = myCourse.getEnrollments();
            if(enrollments.isEmpty()){
                return ResponseEntity.ok("there are no Enrollments");
            }

            Set<EnrollmentDto> allEnrollments = enrollments.stream()
                                        .map(enrollmentMapper::toEnrollmentDto).collect(Collectors.toSet());

            return ResponseEntity.ok(allEnrollments);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Instructor not the owner of "+myCourse.getTitle());
    }

    public ResponseEntity<String> submitCourseForApproval(Long instructorId, CourseDto courseDto) {

        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        if(optionalInstructor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instructor not found");
        }

        Instructor instructor = optionalInstructor.get();

        if(courseRepository.findByTitle(courseDto.getTitle()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Course already exists with this title");
        }

        CourseSubmission courseSubmission = new CourseSubmission();
        CourseCategory category = categoryRepository.findByName(courseDto.getCategory())
                .orElseGet(() -> {
                    CourseCategory newCategory = new CourseCategory();
                    newCategory.setName(courseDto.getCategory());
                    return categoryRepository.save(newCategory);
                });
        courseSubmission.setTitle(courseDto.getTitle());
        courseSubmission.setDescription(courseDto.getDescription());
        courseSubmission.setCategory(category);
        courseSubmission.setInstructor(instructor);
        if(courseDto.getTags() != null){
            courseSubmission.setTags(String.join(", ", courseDto.getTags()));
        }
        courseSubmission.setStatus(CourseStatus.PENDING);

        if(courseSubmissionRepository.findByTitle(courseSubmission.getTitle()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Course already submitted for approval");
        }
        courseSubmissionRepository.save(courseSubmission);
        return ResponseEntity.ok("Course submitted for approval");
    }

    public ResponseEntity<String> updateCourse(Long instructorId, Long courseId, CourseDto courseDto) {

        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        if(optionalInstructor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instructor not found");
        }

        Instructor instructor = optionalInstructor.get();

        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalCourse.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }

        if(optionalCourse.stream().noneMatch(course -> course.getInstructor().equals(instructor))){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Instructor is not the owner of this Course");
        }

        Course course = optionalCourse.get();
        CourseCategory category = categoryRepository.findByName(courseDto.getCategory())
                .orElseGet(() -> {
                    CourseCategory newCategory = new CourseCategory();
                    newCategory.setName(courseDto.getCategory());
                    return categoryRepository.save(newCategory);
                });
        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());
        course.setCategory(category);
        if(courseDto.getTags() != null){
            course.setTags(String.join(", ", courseDto.getTags()));
        }

        courseRepository.save(course);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Course Updated Successfully");
    }

    public ResponseEntity<String> deleteCourse(Long instructorId, Long courseId) {

        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        if(optionalInstructor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instructor not found");
        }

        Instructor instructor = optionalInstructor.get();

        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalCourse.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }

        if(optionalCourse.stream().noneMatch(course -> course.getInstructor().equals(instructor))){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Instructor is not the owner of this Course");
        }

        Course course = optionalCourse.get();
        courseRepository.delete(course);
        return ResponseEntity.ok().body("Course deleted successfully");
    }

    public ResponseEntity<?> searchInstructorCourses(long instructorId, String keyword) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        if(optionalInstructor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instructor not found");
        }
        Set<Course> courses = instructorRepository.searchInstructorCourses(instructorId, keyword);
        Set<CourseDto> allCourses = courses.stream()
                .map(courseMapper::toCourseDto).collect(Collectors.toSet());

        return ResponseEntity.ok(allCourses);
    }
}
