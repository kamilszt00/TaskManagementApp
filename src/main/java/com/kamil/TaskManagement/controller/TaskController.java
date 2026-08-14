package com.kamil.TaskManagement.controller;


import com.kamil.TaskManagement.service.TaskService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
      this.taskService = taskService;
    };


}
