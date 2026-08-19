package com.kamil.TaskManagement.controller;


import com.kamil.TaskManagement.model.Project;
import com.kamil.TaskManagement.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectRepository projectRepository;

    @GetMapping("/project")
    public ResponseEntity<Project> viewProject(@RequestParam Integer id) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            return ResponseEntity.ok(optionalProject.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
