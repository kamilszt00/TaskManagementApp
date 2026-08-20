package com.kamil.TaskManagement.DTO;


import com.kamil.TaskManagement.model.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponse {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}
