package com.kamil.TaskManagement.service;


import com.kamil.TaskManagement.DTO.AssigneesRequest;
import com.kamil.TaskManagement.DTO.CreateProjectRequest;
import com.kamil.TaskManagement.DTO.ProjectResponse;

import com.kamil.TaskManagement.exception.ArgumentsEqualException;
import com.kamil.TaskManagement.exception.InvalidUserRoleException;
import com.kamil.TaskManagement.mapper.ProjectMapper;
import com.kamil.TaskManagement.model.*;
import com.kamil.TaskManagement.repository.ProjectRepository;
import com.kamil.TaskManagement.repository.TaskRepository;
import com.kamil.TaskManagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


    public ResponseEntity<ProjectResponse> createProject(CreateProjectRequest request) {
        Project project = projectMapper.toRequest(request);
        project.setManager(userRepository.getReferenceById(request.getManagerID()));
        User userManager = userRepository.findById(request.getManagerID()).orElseThrow(() -> new EntityNotFoundException("Manager not found"));
        if (userManager.getRole().equals(UserRole.MANAGER)) {
            project.setManager(userManager);
        } else {
            throw new InvalidUserRoleException("User is not a manager");
        }
        ProjectResponse projectResponse = projectMapper.toResponse(projectRepository.save(project));
        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(projectResponse);
    }
    public ResponseEntity<ProjectResponse> getProject(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        return ResponseEntity.ok(projectMapper.toResponse(project));
    }

    public ResponseEntity<ProjectResponse> updateProject(CreateProjectRequest request, Integer id) {
            Project projectToUpdate = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
            Project project = projectMapper.toRequest(request);
            project.setId(projectToUpdate.getId());
            return ResponseEntity.ok(projectMapper.toResponse(projectRepository.save(project)));
    }
    @Transactional
    public ResponseEntity<ProjectResponse> deleteProject(Integer projectToDeleteId, Integer projectToReassignId) {
        if (projectToDeleteId.equals(projectToReassignId)) {throw new ArgumentsEqualException("You cannot reassign to the same project");}
        Project projectToDelete = projectRepository.findById(projectToDeleteId)
                .orElseThrow(() -> new EntityNotFoundException("Project to delete not found"));
        Project projectToReassign = projectRepository.findById(projectToReassignId)
                        .orElseThrow(() -> new EntityNotFoundException("Project to reassign not found"));
        List<Task> incompletedTasks = taskRepository.findIncompletedTasks(projectToDeleteId);
        incompletedTasks.forEach(task -> {
            task.setProject(projectToReassign);
        });
        List<Task> completedTasks = taskRepository.findAllByProject(projectToDelete);
        taskRepository.deleteAll(completedTasks);
        projectRepository.delete(projectToDelete);
        return ResponseEntity.noContent().build();
    }


    @Transactional
    public ResponseEntity<Void> addAssigneesToProject(Integer projectID, AssigneesRequest request) {
        Project project = projectRepository.findById(projectID)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        List<User> usersToAdd = userRepository.findAllById(request.getUsersID());
        if (usersToAdd.size() != request.getUsersID().size()) {
            throw new EntityNotFoundException("User do not exist");
        }
        project.getAssignees().addAll(usersToAdd);
        return ResponseEntity.noContent().build();
    }


}
