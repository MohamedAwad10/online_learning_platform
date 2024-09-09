package com.gdsc.OnlineLearningPlatform.mapper;

import com.gdsc.OnlineLearningPlatform.dto.ReviewDto;
import com.gdsc.OnlineLearningPlatform.model.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewDto toReviewDto(Review review){
        return ReviewDto.builder()
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .studentName(review.getStudent().getFirstName()+" "+review.getStudent().getLastName())
                .build();
    }
}
