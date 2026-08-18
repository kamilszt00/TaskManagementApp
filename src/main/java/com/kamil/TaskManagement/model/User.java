package com.kamil.TaskManagement.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
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
}
