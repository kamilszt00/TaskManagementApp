package com.kamil.TaskManagement.security;


import com.kamil.TaskManagement.model.Project;
import com.kamil.TaskManagement.model.User;
import com.kamil.TaskManagement.model.UserPrincipal;
import com.kamil.TaskManagement.repository.ProjectRepository;
import com.kamil.TaskManagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class ProjectSecurity {
    final private ProjectRepository projectRepository;
    final private UserRepository userRepository;
    public boolean isManager( Integer projectID, Authentication authentication) {
        Project project = projectRepository.findById(projectID).orElseThrow(() -> new EntityNotFoundException("Project not found"));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();
        return project.getManager().getId().equals(user.getId());
    }
    public boolean isAssignee(Integer projectID, Authentication authentication) {
        Project project = projectRepository.findById(projectID).orElseThrow(() -> new EntityNotFoundException("Project not found"));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();
        return projectRepository.existsByIdAndAssignees_Id(project.getId(),user.getId());
    }





}
