package com.kamil.TaskManagement.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Email
    private String email;
    @NotBlank(message = "User role should not be empty")
    @Pattern(regexp = "EMPLOYEE|MANAGER|ADMIN", message = "Pick from EMPLOYEE,MANAGER,ADMIN")
    private String role;

}
