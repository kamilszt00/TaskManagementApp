package com.kamil.TaskManagement.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PROJECTS_TBL")
public class Project {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 35)
    private String name;


    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @OneToMany(mappedBy = "project")
    private Set<Task> tasks = new HashSet<>();
}
