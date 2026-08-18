package com.kamil.TaskManagement.repository;

import com.kamil.TaskManagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Integer> {

}
