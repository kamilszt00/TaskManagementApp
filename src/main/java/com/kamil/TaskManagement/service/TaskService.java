package com.kamil.TaskManagement.service;


import com.kamil.TaskManagement.DTO.CreateTaskRequest;
import com.kamil.TaskManagement.DTO.TaskResponse;
import com.kamil.TaskManagement.DTO.UpdateTaskRequest;
import com.kamil.TaskManagement.model.Tag;
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

import static org.yaml.snakeyaml.tokens.Token.ID.Tag;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public ResponseEntity<TaskResponse> createTask(CreateTaskRequest request) {
        Task task = Task.builder()
                .id(null)
                .status(request.getStatus())
                .title(request.getTitle())
                .tags(new HashSet<>(tagRepository.findAllById(request.getTagsID())))
                .dueDate(request.getDueDate())
                .project(projectRepository.getReferenceById(request.getProjectID()))
                .user(userRepository.getReferenceById(request.getAssigneeID()))
                .build();

        Task createdTask = taskRepository.save(task);


        TaskResponse taskResponse = TaskResponse.builder()
                .id(createdTask.getId())
                .title(createdTask.getTitle())
                .status(createdTask.getStatus())
                .projectID(createdTask.getId())
                .assigneeID(createdTask.getUser().getId())
                .build();
        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(taskResponse);
    }


    public ResponseEntity<TaskResponse> getTask(Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            TaskResponse theTask = TaskResponse.builder()
                                    .id(task.getId())
                                    .title(task.getTitle())
                                    .status(task.getStatus())
                                    .projectID(task.getProject().getId())
                                    .assigneeID(task.getUser().getId())
                                    .build();
            return ResponseEntity.ok(theTask);
        } else {
            return ResponseEntity.notFound().build();
        }
        }


    public ResponseEntity<TaskResponse> updateTask(UpdateTaskRequest request, Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setTitle(request.getTitle());
            task.setDueDate(request.getDueDate());
            task.setStatus(request.getStatus());
            task.setProject(projectRepository.getReferenceById(request.getProjectID()));
            task.setUser(userRepository.getReferenceById(request.getAssigneeID()));
            task.setTags(new HashSet<>(tagRepository.findAllById(request.getTagsID())));
            taskRepository.save(task);
            TaskResponse taskUpdatedResponse = TaskResponse.builder()
                    .id(task.getId())
                    .title(task.getTitle())
                    .status(task.getStatus())
                    .projectID(task.getId())
                    .assigneeID(task.getUser().getId())
                    .build();

            return ResponseEntity.ok(taskUpdatedResponse);
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
