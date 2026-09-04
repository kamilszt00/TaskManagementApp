package com.kamil.TaskManagement.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
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


    private String password;

    @Column (unique = true,nullable = false)
    private String email;

    private String role;


    @Builder.Default
    @OneToMany(mappedBy = "user")
    private Set<Task> tasks = new HashSet<>();

}
