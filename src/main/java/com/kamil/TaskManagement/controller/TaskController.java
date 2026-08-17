package com.kamil.TaskManagement.controller;


import com.kamil.TaskManagement.model.Task;
import com.kamil.TaskManagement.service.TaskService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
      this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public List<Task> viewTasks() {
        return taskService.getTaskRepositoryList();
    }




}
