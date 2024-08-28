package com.gdsc.OnlineLearningPlatform.service;

import com.gdsc.OnlineLearningPlatform.dto.CourseDto;
import com.gdsc.OnlineLearningPlatform.model.Course;
import com.gdsc.OnlineLearningPlatform.model.CourseCategory;
import com.gdsc.OnlineLearningPlatform.model.Instructor;
import com.gdsc.OnlineLearningPlatform.repository.CategoryRepository;
import com.gdsc.OnlineLearningPlatform.repository.CourseRepository;
import com.gdsc.OnlineLearningPlatform.repository.InstructorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;

    private final CategoryRepository categoryRepository;

    private final CourseRepository courseRepository;

    public InstructorService(InstructorRepository instructorRepository,
                             CategoryRepository categoryRepository, CourseRepository courseRepository) {
        this.instructorRepository = instructorRepository;
        this.categoryRepository = categoryRepository;
        this.courseRepository = courseRepository;
    }

//    public ResponseEntity<String> checkInstructorExist(Long instructorId){
//        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
//        if(optionalInstructor.isEmpty()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instructor not found");
//        }
//        return ResponseEntity.ok("Founded");
//    }

    public ResponseEntity<?> myAllCourses(Long instructorId) {

        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        if(optionalInstructor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instructor not found");
        }

        Optional<Set<Course>> myCourses = instructorRepository.findCoursesByInstructorId(instructorId);

        if(myCourses.isPresent()){
            Set<Course> courses = myCourses.get();
            return ResponseEntity.ok(courses);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No courses found for instructor");
    }

    public ResponseEntity<?> createCourse(Long instructorId, CourseDto courseDto) {

        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        if(optionalInstructor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instructor not found");
        }

        Instructor instructor = optionalInstructor.get();

        if(courseRepository.findByTitle(courseDto.getTitle()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Course already exists with this title");
        }

        Course course = new Course();
        CourseCategory category = categoryRepository.findByName(courseDto.getCategory())
                .orElseGet(() -> {
                    CourseCategory newCategory = new CourseCategory();
                    newCategory.setName(courseDto.getCategory());
                    return categoryRepository.save(newCategory);
                });
        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());
        course.setCategory(category);
        course.setInstructor(instructor);
        if(courseDto.getTags() != null){
            course.setTags(String.join(",", courseDto.getTags()));
        }

        courseRepository.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseRepository.save(course));
    }
}
