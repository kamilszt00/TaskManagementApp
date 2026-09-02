package com.kamil.TaskManagement.service;


import com.kamil.TaskManagement.DTO.CreateProjectRequest;
import com.kamil.TaskManagement.DTO.ProjectResponse;

import com.kamil.TaskManagement.exception.ArgumentsEqualException;
import com.kamil.TaskManagement.mapper.ProjectMapper;
import com.kamil.TaskManagement.model.Project;
import com.kamil.TaskManagement.model.Task;
import com.kamil.TaskManagement.model.TaskStatus;
import com.kamil.TaskManagement.repository.ProjectRepository;
import com.kamil.TaskManagement.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final TaskRepository taskRepository;


    public ResponseEntity<ProjectResponse> createProject(CreateProjectRequest request) {
        Project project = projectMapper.toRequest(request);
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
}
