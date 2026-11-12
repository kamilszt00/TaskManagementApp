package com.kamil.TaskManagement.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssigneesRequest {
    @NotEmpty
    List<Integer> usersID;
}
