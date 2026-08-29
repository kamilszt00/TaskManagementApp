package com.kamil.TaskManagement.DTO;


import jakarta.validation.constraints.*;
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
public class UpdateTaskRequest {
    @NotBlank(message = "Task title should not be empty")
    private String title;
    @NotBlank(message = "Task status should not be empty")
    @Pattern(regexp = "TODO|IN_PROGRESS|COMPLETED", message = "Pick from TODO,IN_PROGRESS,COMPLETED")
    private String status;
    @NotNull
    @Positive
    private Integer projectID;
    @Positive
    @NotNull
    private Integer assigneeID;
    @Size(min = 1)
    private Set<Integer> tagsID;


}
