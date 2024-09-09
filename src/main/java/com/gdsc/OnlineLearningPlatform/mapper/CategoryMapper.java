package com.gdsc.OnlineLearningPlatform.mapper;

import com.gdsc.OnlineLearningPlatform.dto.CategoryDto;
import com.gdsc.OnlineLearningPlatform.model.CourseCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    @Autowired
    private CourseMapper courseMapper;

    public CategoryDto toCategoryDto(CourseCategory category){
        return CategoryDto.builder()
                .category(category.getName())
//                .courses(category.getCourses().stream()
//                        .map(course -> courseMapper.toCourseDto(course)).collect(Collectors.toSet()))
                .build();
    }
}
