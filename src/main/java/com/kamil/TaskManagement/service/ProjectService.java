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

import java.util.HashSet;

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
        System.out.println(project.getTasks());
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



//    public ResponseEntity<ProjectResponse> getProject(Integer id) {
//
//    }
//    public ResponseEntity<ProjectResponse> updateProject(UpdateProjectRequest request, Integer id) {
//
//    }
//    public ResponseEntity<ProjectResponse> deleteProject(Integer id) {
//
//    }
}
