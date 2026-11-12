package com.kamil.TaskManagement.repository;

import com.kamil.TaskManagement.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findByManager_Id(Integer Id);
    List<Project> findByAssignees_Id(Integer Id);

}
