package com.kamil.TaskManagement.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {
    private Integer id;
    private String content;
    private String authorUsername;
    private String taskTitle;
    private LocalDateTime createdAt;

}
