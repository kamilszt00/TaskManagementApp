package com.kamil.TaskManagement.DTO;


import com.kamil.TaskManagement.model.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Integer id;
    String userName;
    String userRole;
    String userEmail;
    Set<Task> userTasks;


}
