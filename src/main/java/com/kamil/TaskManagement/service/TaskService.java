package com.kamil.TaskManagement.service;


import com.kamil.TaskManagement.DTO.CreateTaskRequest;
import com.kamil.TaskManagement.DTO.TaskResponse;
import com.kamil.TaskManagement.model.Tag;
import com.kamil.TaskManagement.model.Task;
import com.kamil.TaskManagement.repository.ProjectRepository;
import com.kamil.TaskManagement.repository.TagRepository;
import com.kamil.TaskManagement.repository.TaskRepository;
import com.kamil.TaskManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import static org.yaml.snakeyaml.tokens.Token.ID.Tag;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public TaskResponse createTask(CreateTaskRequest request) {
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


        return TaskResponse.builder()
                .id(createdTask.getId())
                .title(createdTask.getTitle())
                .status(createdTask.getStatus())
                .projectID(createdTask.getId())
                .tagsID(createdTask.getTags())
                .build();
    }






}
