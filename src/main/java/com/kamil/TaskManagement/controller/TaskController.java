package com.kamil.TaskManagement.controller;


import com.kamil.TaskManagement.DTO.CreateTaskRequest;
import com.kamil.TaskManagement.DTO.TaskResponse;
import com.kamil.TaskManagement.DTO.UpdateTaskRequest;
import com.kamil.TaskManagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;



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

    @PutMapping("/task/{id}")
    public ResponseEntity<TaskResponse> putTask(@RequestBody UpdateTaskRequest request, @PathVariable Integer id) {
        return taskService.updateTask(request, id);
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<TaskResponse> deleteTask(@PathVariable Integer id) {
        return taskService.deleteTask(id);
    }












}
