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
public class CreateProjectRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @PastOrPresent
    private LocalDateTime createdAt;
    @NotNull
    @Positive
    private Integer managerID;


}
