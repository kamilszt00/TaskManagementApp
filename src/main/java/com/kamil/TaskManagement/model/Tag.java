package com.kamil.TaskManagement.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "TAGS_TBL")
@NoArgsConstructor
@Entity
public class Tag {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(insertable = false, updatable = false)
    private String tag_name;
}
