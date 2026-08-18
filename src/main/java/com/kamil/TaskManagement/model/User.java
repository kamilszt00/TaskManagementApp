package com.kamil.TaskManagement.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "USERS_TBL")
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Column (length = 35,name = "u_name")
    private String username;

    @Column (unique = true,nullable = false)
    private String email;

    private String role;

    @OneToMany(mappedBy = "user")
    private Set<Task> tasks;
}
