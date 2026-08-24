package com.kamil.TaskManagement.service;


import com.kamil.TaskManagement.DTO.CreateProjectRequest;
import com.kamil.TaskManagement.DTO.ProjectResponse;

import com.kamil.TaskManagement.mapper.ProjectMapper;
import com.kamil.TaskManagement.model.Project;
import com.kamil.TaskManagement.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Optional;

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
        Optional<Project> optionalProject = projectRepository.findById(id);
        return optionalProject.map(project -> ResponseEntity.ok(projectMapper.toResponse(project))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<ProjectResponse> updateProject(CreateProjectRequest request, Integer id) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = projectMapper.toRequest(request);
            project.setId(optionalProject.get().getId());
            return ResponseEntity.ok(projectMapper.toResponse(projectRepository.save(project)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<ProjectResponse> deleteProject(Integer id) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            projectRepository.delete(optionalProject.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
