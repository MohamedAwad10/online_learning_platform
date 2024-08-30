package com.gdsc.OnlineLearningPlatform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.Set;

@Entity
public class Admin extends User{

    @OneToMany
    private Set<CourseSubmission> courseSubmissions;
}
