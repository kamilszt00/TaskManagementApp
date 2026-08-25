package com.kamil.TaskManagement.service;


import com.kamil.TaskManagement.DTO.CreateProjectRequest;
import com.kamil.TaskManagement.DTO.ProjectResponse;

import com.kamil.TaskManagement.mapper.ProjectMapper;
import com.kamil.TaskManagement.model.Project;
import com.kamil.TaskManagement.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;



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
    public ResponseEntity<ProjectResponse> deleteProject(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
            projectRepository.delete(project);
            return ResponseEntity.noContent().build();
    }
}
