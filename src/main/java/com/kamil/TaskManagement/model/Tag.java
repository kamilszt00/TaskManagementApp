package com.kamil.TaskManagement.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TAGS_TBL")
public class Tag {

    @Id
    @GeneratedValue
    private Integer id;

    @Column( updatable = false)
    private String tag_name;

}
