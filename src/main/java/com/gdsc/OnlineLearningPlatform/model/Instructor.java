package com.gdsc.OnlineLearningPlatform.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
public class Instructor extends User{

    private String bio;

    @Column(nullable = false)
    private Integer yearsOfExperience;

    @OneToMany(mappedBy = "instructor")
    private Set<Course> courses;

    @OneToMany(mappedBy = "instructor")
    private Set<Notification> notifications;
}
