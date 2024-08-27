package com.gdsc.OnlineLearningPlatform.service;

import com.gdsc.OnlineLearningPlatform.dto.CourseDto;
import com.gdsc.OnlineLearningPlatform.model.Course;
import com.gdsc.OnlineLearningPlatform.model.CourseCategory;
import com.gdsc.OnlineLearningPlatform.repository.CategoryRepository;
import com.gdsc.OnlineLearningPlatform.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private CourseRepository courseRepository;

    private CategoryRepository categoryRepository;

    public CourseService(CourseRepository courseRepository, CategoryRepository categoryRepository) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<?> addCourse(CourseDto courseDto) throws Exception{

        if(courseRepository.findByTitle(courseDto.getTitle()).isEmpty()){

            Course course = new Course();

            CourseCategory category = categoryRepository.findByName(courseDto.getCategory())
                    .orElseGet(() -> {
                        CourseCategory newCategory = new CourseCategory();
                        newCategory.setName(courseDto.getCategory());
                        return categoryRepository.save(newCategory);
                    });

            course.setTitle(courseDto.getTitle());
            course.setCategory(category);
            course.setDescription(courseDto.getDescription());
            course.setTags(String.join(",", courseDto.getTags()));

            return ResponseEntity.status(HttpStatus.CREATED).body(courseRepository.save(course));
        } else {
            throw new IllegalArgumentException("Course is already defined");
        }
    }
}
