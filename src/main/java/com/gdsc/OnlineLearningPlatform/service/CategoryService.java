package com.gdsc.OnlineLearningPlatform.service;

import com.gdsc.OnlineLearningPlatform.dto.CategoryDto;
import com.gdsc.OnlineLearningPlatform.dto.CourseDto;
import com.gdsc.OnlineLearningPlatform.mapper.CategoryMapper;
import com.gdsc.OnlineLearningPlatform.mapper.CourseMapper;
import com.gdsc.OnlineLearningPlatform.model.Course;
import com.gdsc.OnlineLearningPlatform.model.CourseCategory;
import com.gdsc.OnlineLearningPlatform.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private CategoryMapper categoryMapper;

    private CourseMapper courseMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper
                                , CourseMapper courseMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.courseMapper = courseMapper;
    }

    public ResponseEntity<?> getAllCategories() {

        List<CourseCategory> categories = categoryRepository.findAll();

        if(categories.isEmpty()){
            return ResponseEntity.ok("There are no categories");
        }

        List<CategoryDto> allCategories = categories.stream()
                .map(category -> categoryMapper.toCategoryDto(category)).toList();

        return ResponseEntity.ok(allCategories);
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

        Set<CourseDto> allCourses = courses.stream()
                .map(course -> courseMapper.toCourseDto(course)).collect(Collectors.toSet());

        return ResponseEntity.ok(allCourses);
    }
}
