package com.kamil.TaskManagement.repository;

import com.kamil.TaskManagement.model.Tag;
import com.kamil.TaskManagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TaskRepository extends JpaRepository<Task,Integer> {

    List<Task> findAllById(Integer id);
}
