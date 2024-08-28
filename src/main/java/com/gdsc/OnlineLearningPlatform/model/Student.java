package com.gdsc.OnlineLearningPlatform.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@Entity
public class Student extends User{

    private LocalDate birthDate;

    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses;

    @OneToMany(mappedBy = "student")
    private Set<Enrollment> enrollments;

    @OneToMany(mappedBy = "student")
    private Set<Review> reviews;
}
