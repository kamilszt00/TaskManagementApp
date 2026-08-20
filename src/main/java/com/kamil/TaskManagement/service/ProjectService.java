package com.kamil.TaskManagement.service;


import com.kamil.TaskManagement.DTO.CreateProjectRequest;
import com.kamil.TaskManagement.DTO.ProjectResponse;

import com.kamil.TaskManagement.DTO.UpdateProjectRequest;
import com.kamil.TaskManagement.model.Project;
import com.kamil.TaskManagement.repository.ProjectRepository;
import com.kamil.TaskManagement.repository.TagRepository;
import com.kamil.TaskManagement.repository.TaskRepository;
import com.kamil.TaskManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;



    public ResponseEntity<ProjectResponse> createProject(CreateProjectRequest request) {
        Project project = Project.builder()
                .id(null)
                .name(request.getName())
                .description(request.getDescription())
                .createdAt(request.getCreatedAt())
                .build();
        Project addedProject = projectRepository.save(project);


        ProjectResponse projectResponse = ProjectResponse.builder()
                .id(addedProject.getId())
                .name(addedProject.getName())
                .description(addedProject.getDescription())
                .createdAt(addedProject.getCreatedAt())
                .build();

        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(projectResponse);
    }



    public ResponseEntity<ProjectResponse> getProject(Integer id) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project proj = optionalProject.get();
            return ResponseEntity.ok(
                    ProjectResponse.builder()
                            .id(proj.getId())
                            .name(proj.getName())
                            .description(proj.getDescription())
                            .createdAt(proj.getCreatedAt())
                            .tasks(taskRepository.getTaskNames(proj.getId()))
                            .build()
            );
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<ProjectResponse> updateProject(UpdateProjectRequest request, Integer id) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setName(request.getName());
            project.setDescription(request.getDescription());
            project.setCreatedAt(request.getCreatedAt());
            projectRepository.save(project);

            return ResponseEntity.ok(ProjectResponse.builder()
                    .id(project.getId())
                    .name(project.getName())
                    .description(project.getDescription())
                    .createdAt(project.getCreatedAt())
                    .tasks(taskRepository.getTaskNames(project.getId()))
                    .build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
//    public ResponseEntity<ProjectResponse> deleteProject(Integer id) {
//
//    }
}
