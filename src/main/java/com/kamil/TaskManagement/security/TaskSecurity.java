package com.kamil.TaskManagement.security;



import com.kamil.TaskManagement.model.Task;
import com.kamil.TaskManagement.model.User;
import com.kamil.TaskManagement.model.UserPrincipal;
import com.kamil.TaskManagement.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TaskSecurity {
    private final TaskRepository taskRepository;

    public boolean isAssignee(Integer taskId, Authentication authentication) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(("Task not found")));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();

        return task.getUser().getId().equals(user.getId());
    }
}
