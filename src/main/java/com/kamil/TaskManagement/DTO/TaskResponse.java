package com.kamil.TaskManagement.DTO;


import com.kamil.TaskManagement.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
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
    private Set<Integer> tagsID;

}
