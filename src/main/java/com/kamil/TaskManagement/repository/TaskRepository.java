package com.kamil.TaskManagement.repository;

import com.kamil.TaskManagement.model.Tag;
import com.kamil.TaskManagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface TaskRepository extends JpaRepository<Task,Integer> {

    @Query(value = "SELECT title FROM task_api_db.tasks_tbl WHERE project_id = :project_id",nativeQuery = true)
    List<String> getTaskNames(@Param("project_id") Integer id);





}
