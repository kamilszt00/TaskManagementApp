package com.kamil.TaskManagement.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "COMMENTS_TBL")
public class Comment {
    @Id
    @GeneratedValue
    private Integer id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Builder.Default
    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

}
