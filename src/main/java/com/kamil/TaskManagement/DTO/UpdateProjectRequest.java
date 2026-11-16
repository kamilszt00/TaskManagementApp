package com.kamil.TaskManagement.DTO;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProjectRequest {
    @Size(max = 35)
    @Pattern(regexp = ".*\\S.*", message = "Name must not be blank")
    private String name;
    @Size(max = 255)
    @Pattern(regexp = ".*\\S.*", message = "Description must not be blank")
    private String description;
}
