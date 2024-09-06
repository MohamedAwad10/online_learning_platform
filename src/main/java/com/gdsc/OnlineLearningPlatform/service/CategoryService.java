package com.gdsc.OnlineLearningPlatform.service;

import com.gdsc.OnlineLearningPlatform.model.Course;
import com.gdsc.OnlineLearningPlatform.model.CourseCategory;
import com.gdsc.OnlineLearningPlatform.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<?> getAllCategories() {

        List<CourseCategory> categories = categoryRepository.findAll();

        if(categories.isEmpty()){
            return ResponseEntity.ok("There are no categories");
        }

        return ResponseEntity.ok(categories);
    }

    public ResponseEntity<?> getCategoryCourses(Long categoryId) {
        Optional<CourseCategory> optionalCourseCategory = categoryRepository.findById(categoryId);

        if(optionalCourseCategory.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }

        CourseCategory category = optionalCourseCategory.get();
        Set<Course> courses = category.getCourses();
        if(courses.isEmpty()){
            return ResponseEntity.ok("There are no Courses");
        }

        return ResponseEntity.ok(courses);
    }
}
