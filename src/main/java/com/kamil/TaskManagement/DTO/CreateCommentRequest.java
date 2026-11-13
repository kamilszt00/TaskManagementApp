package com.kamil.TaskManagement.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCommentRequest {
    @NotBlank(message = "Comment content should not be empty")
    @Size(max = 255, message = "Comment content should not be longer than 255 characters")
    private String content;

}
