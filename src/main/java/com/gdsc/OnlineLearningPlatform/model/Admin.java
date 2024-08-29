package com.gdsc.OnlineLearningPlatform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Entity
public class Admin extends User{

    @OneToMany
    private Set<CourseSubmission> courseSubmissions;
}
