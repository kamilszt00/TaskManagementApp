package com.kamil.TaskManagement.service;


import com.kamil.TaskManagement.DTO.CreateTaskRequest;
import com.kamil.TaskManagement.DTO.TaskResponse;
import com.kamil.TaskManagement.mapper.TaskMapper;
import com.kamil.TaskManagement.model.Task;
import com.kamil.TaskManagement.repository.ProjectRepository;
import com.kamil.TaskManagement.repository.TagRepository;
import com.kamil.TaskManagement.repository.TaskRepository;
import com.kamil.TaskManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public ResponseEntity<TaskResponse> createTask(CreateTaskRequest request) {
        Task task = taskMapper.toRequest(request);
        task.setTags(new HashSet<>(tagRepository.findAllById(request.getTagsID())));
        task.setProject(projectRepository.getReferenceById(request.getProjectID()));
        task.setUser(userRepository.getReferenceById(request.getAssigneeID()));
        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(taskMapper.toResponse(taskRepository.save(task)));

    }


    public ResponseEntity<TaskResponse> getTask(Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.map(task -> ResponseEntity.ok(taskMapper.toResponse(task))).orElseGet(() -> ResponseEntity.notFound().build());
        }


    public ResponseEntity<TaskResponse> updateTask(CreateTaskRequest request, Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setTitle(request.getTitle());
            task.setStatus(request.getStatus());
            task.setProject(projectRepository.getReferenceById(request.getProjectID()));
            task.setUser(userRepository.getReferenceById(request.getAssigneeID()));
            task.setTags(new HashSet<>(tagRepository.findAllById(request.getTagsID())));
            return ResponseEntity.ok(taskMapper.toResponse(taskRepository.save(task)));
        } else {
            return ResponseEntity.notFound().build();
        }

    }


    public ResponseEntity<TaskResponse> deleteTask(Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            taskRepository.delete(optionalTask.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }






}
