package com.kamil.TaskManagement.controller;


import com.kamil.TaskManagement.model.Task;
import com.kamil.TaskManagement.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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








    @GetMapping("/onetask")
    public ResponseEntity<Task> viewOneTask(@RequestBody Task task) {

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(task);
    }




}
