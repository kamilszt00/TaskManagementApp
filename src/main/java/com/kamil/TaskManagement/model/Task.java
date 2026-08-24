package com.kamil.TaskManagement.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TASKS_TBL")
public class Task {
    @Id
    @GeneratedValue
    @Column(updatable = false)
    private Integer id;
    private String status;
    private String title;


    @ManyToMany
    @JoinTable(name = "TASK_TAG_LINK",joinColumns = @JoinColumn(name="task_id"),inverseJoinColumns = @JoinColumn(name="tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @Column(nullable = false,updatable = false)
    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
