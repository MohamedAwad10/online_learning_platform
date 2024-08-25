package com.gdsc.OnlineLearningPlatform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class Role {

//    Use FetchType.LAZY to optimize performance and load related entities only when needed.
//    Use FetchType.EAGER when you are sure related entities are always required and want to avoid additional database queries.
//    Use Cascades to automatically manage the persistence state of related entities, especially in parent-child relationships.

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> user;

}
