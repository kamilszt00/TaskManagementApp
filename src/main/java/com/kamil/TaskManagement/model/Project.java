package com.kamil.TaskManagement.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PROJECTS_TBL")
public class Project {

    @Id
    @GeneratedValue
    @Column(updatable = false)
    private Integer id;

    @Column(length = 35)
    private String name;


    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "project")
    private Set<Task> tasks = new HashSet<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "PROJECT_ASSIGNEE_LINK",joinColumns = @JoinColumn(name="project_id"),inverseJoinColumns = @JoinColumn(name="user_id"))
    private Set<User> assignees = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;
}

