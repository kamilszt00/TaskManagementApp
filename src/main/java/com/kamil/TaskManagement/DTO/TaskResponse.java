package com.kamil.TaskManagement.DTO;


import com.kamil.TaskManagement.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponse {
    private Integer id;
    private String title;
    private String status;
    private Integer projectID;
    private Integer assigneeID;


}
