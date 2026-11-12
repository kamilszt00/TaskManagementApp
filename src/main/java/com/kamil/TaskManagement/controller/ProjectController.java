package com.kamil.TaskManagement.controller;


import com.kamil.TaskManagement.DTO.*;
import com.kamil.TaskManagement.service.ProjectService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PreAuthorize("hasAuthority('ADMIN')")
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/project/{id}")
    public ResponseEntity<ProjectResponse> deleteProject(@PathVariable Integer id,@RequestParam Integer projectId) {
        return projectService.deleteProject(id, projectId);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/project/{id}/assignees")
    public ResponseEntity<Void> addAssigneesToProject(@PathVariable Integer id,@RequestBody AssigneesRequest request) {
        return projectService.addAssigneesToProject(id, request);
    }


    @PreAuthorize("@projectSecurity.isManager(#project_id,authentication) or hasAuthority('ADMIN')")
    @DeleteMapping("/project/{project_id}/assignees/{assignee_id}")
    public ResponseEntity<Void> deleteAssigneeFromProject(@PathVariable Integer project_id, @PathVariable Integer assignee_id) {
        return projectService.deleteAssigneeFromProject(project_id,assignee_id);
    }


}