package com.gdsc.OnlineLearningPlatform.model;

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

    @Column(nullable = false)
    private String status;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate submittedAt;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
