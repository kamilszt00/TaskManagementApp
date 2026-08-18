package com.kamil.TaskManagement.service;


import com.kamil.TaskManagement.model.Task;
import com.kamil.TaskManagement.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;



    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTaskRepositoryList() {
        return taskRepository.findAll();
    }



    public Task createTask(Task task) {
        return taskRepository.save(task);
    }
}
