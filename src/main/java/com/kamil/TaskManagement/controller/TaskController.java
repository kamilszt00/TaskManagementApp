package com.kamil.TaskManagement.controller;


import com.kamil.TaskManagement.DTO.CreateTaskRequest;
import com.kamil.TaskManagement.DTO.TaskResponse;
import com.kamil.TaskManagement.service.TaskService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
      this.taskService = taskService;
    }


    @PostMapping("/tasks")
    public ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskRequest request) {
        TaskResponse taskResponse = taskService.createTask(request);
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(201))
                    .body(taskResponse);

    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Integer id) {
        return taskService.getTask(id);

    }












}
