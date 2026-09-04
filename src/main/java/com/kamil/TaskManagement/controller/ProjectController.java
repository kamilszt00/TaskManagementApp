package com.kamil.TaskManagement.controller;


import com.kamil.TaskManagement.DTO.*;
import com.kamil.TaskManagement.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/project")
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody CreateProjectRequest request) {
        return projectService.createProject(request);

    }
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN','EMPLOYEE')")
    @GetMapping("/project/{id}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable Integer id) {
        return projectService.getProject(id);

    }
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @PutMapping("/project/{id}")
    public ResponseEntity<ProjectResponse> putProject(@Valid @RequestBody CreateProjectRequest request, @PathVariable Integer id) {
        return projectService.updateProject(request, id);
    }

    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @DeleteMapping("/project/{id}")
    public ResponseEntity<ProjectResponse> deleteProject(@PathVariable Integer id,@RequestParam Integer projectId) {
        return projectService.deleteProject(id, projectId);
    }



}