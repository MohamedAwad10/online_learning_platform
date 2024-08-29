package com.gdsc.OnlineLearningPlatform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gdsc.OnlineLearningPlatform.enums.CourseStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Setter
@Getter
public class CourseSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated
    @Column(nullable = false)
    private CourseStatus status;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate submittedAt;

    private String feedback;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;

    @Column(
            nullable = false
    )
    private String title;

    @Column(nullable = false)
    private String description;

    private String tags;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private CourseCategory category;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    @JsonIgnore
    private Instructor instructor;
}
