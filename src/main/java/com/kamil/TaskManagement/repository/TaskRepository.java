package com.kamil.TaskManagement.repository;

import com.kamil.TaskManagement.model.Project;
import com.kamil.TaskManagement.model.Tag;
import com.kamil.TaskManagement.model.Task;
import com.kamil.TaskManagement.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {

    @Query(value = "SELECT title FROM task_api_db.tasks_tbl WHERE project_id = :project_id",nativeQuery = true)
    List<String> getTaskNames(@Param("project_id") Integer id);


    @Query(value = "SELECT title FROM task_api_db.tasks_tbl WHERE user_id = :user_id",nativeQuery = true)
    List<String> getTaskNamesFromUser(@Param("user_id") Integer id);

    @Query("SELECT t from Task t WHERE t.dueDate< :now AND t.status IN :statuses")
    List<Task> findOverdueTasks(@Param("now") LocalDateTime now, @Param("statuses") List<TaskStatus> statuses);

    @Query("SELECT t from Task t WHERE t.status != COMPLETED and t.project.id = :id")
    List<Task> findIncompletedTasks(@Param("id") Integer id);


    List<Task> findTasksByStatus(TaskStatus status);

    List<Task> findAllByProject(Project project);
}
