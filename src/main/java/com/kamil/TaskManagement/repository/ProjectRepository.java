package com.kamil.TaskManagement.repository;

import com.kamil.TaskManagement.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
