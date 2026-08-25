package com.kamil.TaskManagement.controller;


import com.kamil.TaskManagement.DTO.*;
import com.kamil.TaskManagement.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/project")
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody CreateProjectRequest request) {
        return projectService.createProject(request);

    }

    @GetMapping("/project/{id}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable Integer id) {
        return projectService.getProject(id);

    }

    @PutMapping("/project/{id}")
    public ResponseEntity<ProjectResponse> putProject(@Valid @RequestBody CreateProjectRequest request, @PathVariable Integer id) {
        return projectService.updateProject(request, id);
    }

    @DeleteMapping("/project/{id}")
    public ResponseEntity<ProjectResponse> deleteProject(@PathVariable Integer id) {
        return projectService.deleteProject(id);
    }


}