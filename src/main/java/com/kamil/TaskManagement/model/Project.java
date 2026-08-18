package com.kamil.TaskManagement.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "PROJECTS_TBL")
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 35)
    private String name;


    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
