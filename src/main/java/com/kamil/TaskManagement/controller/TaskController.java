package com.kamil.TaskManagement.controller;


import com.kamil.TaskManagement.DTO.CreateTaskRequest;
import com.kamil.TaskManagement.DTO.TaskResponse;
import com.kamil.TaskManagement.DTO.UpdateTaskRequest;
import com.kamil.TaskManagement.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/task")
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
        return taskService.createTask(request);
    }
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN','EMPLOYEE')")
    @GetMapping("/task/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Integer id) {
        return taskService.getTask(id);

    }
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @PutMapping("/task/{id}")
    public ResponseEntity<TaskResponse> putTask(@Valid @RequestBody UpdateTaskRequest request, @PathVariable Integer id) {
        return taskService.updateTask(request, id);
    }
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @DeleteMapping("/task/{id}")
    public ResponseEntity<TaskResponse> deleteTask(@PathVariable Integer id) {
        return taskService.deleteTask(id);
    }
    @PreAuthorize("hasAnyAuthority('MANAGER','EMPLOYEE')")
    @PatchMapping("task/{id}/start")
    public ResponseEntity<TaskResponse> startTask(@PathVariable Integer id) {return taskService.startTask(id);}
    @PreAuthorize("hasAnyAuthority('MANAGER','EMPLOYEE')")
    @PatchMapping("task/{id}/complete")
    public ResponseEntity<TaskResponse> completeTask(@PathVariable Integer id) {return taskService.completeTask(id);}
    @GetMapping("task/overdue")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<List<TaskResponse>> getOverdueTask() {return taskService.getOverdueTask();}
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @PatchMapping("task/{id}/reassign")
    public ResponseEntity<TaskResponse> reassignTask(@PathVariable Integer id, @RequestParam Integer userId) {
        return taskService.reassignTask(id, userId);
    }








}
