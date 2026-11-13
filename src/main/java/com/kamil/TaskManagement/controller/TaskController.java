package com.kamil.TaskManagement.controller;


import com.kamil.TaskManagement.DTO.*;
import com.kamil.TaskManagement.model.UserPrincipal;
import com.kamil.TaskManagement.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @PostMapping("/task")
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
        return taskService.createTask(request);
    }
    @PreAuthorize("@taskSecurity.isAssigneeOrManager(#id,authentication) or hasAuthority('ADMIN')")
    @GetMapping("/task/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Integer id) {
        return taskService.getTask(id);
    }
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @GetMapping("/task/all")
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return taskService.getAllTasks();
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
    @PreAuthorize("@taskSecurity.isAssigneeOrManager(#id,authentication) or hasAuthority('ADMIN')")
    @PatchMapping("task/{id}/start")
    public ResponseEntity<TaskResponse> startTask(@PathVariable Integer id) {return taskService.startTask(id);}
    @PreAuthorize("@taskSecurity.isAssigneeOrManager(#id,authentication) or hasAuthority('ADMIN')")
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
    @PreAuthorize("@taskSecurity.isAssigneeOrManager(#task_id,authentication) or hasAuthority('ADMIN')")
    @PostMapping("task/{task_id}/comments")
    public ResponseEntity<CommentResponse> createCommentToTask(@PathVariable Integer task_id,@Valid @RequestBody CreateCommentRequest createCommentRequest,@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return taskService.createCommentToTask(task_id,createCommentRequest,userPrincipal);
    }
    @PreAuthorize("@taskSecurity.isAssigneeOrManager(#task_id,authentication) or hasAuthority('ADMIN')")
    @GetMapping("task/{task_id}/comments")
    public ResponseEntity<List<CommentResponse>> getCommentsToTask(@PathVariable Integer task_id) {
        return taskService.getCommentsToTask(task_id);
    }








}
