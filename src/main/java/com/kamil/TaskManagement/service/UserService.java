package com.kamil.TaskManagement.service;


import com.kamil.TaskManagement.DTO.CreateUserRequest;
import com.kamil.TaskManagement.DTO.UserResponse;
import com.kamil.TaskManagement.model.User;
import com.kamil.TaskManagement.repository.ProjectRepository;
import com.kamil.TaskManagement.repository.TagRepository;
import com.kamil.TaskManagement.repository.TaskRepository;
import com.kamil.TaskManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public ResponseEntity<UserResponse> createUser(CreateUserRequest request) {
        User user = User.builder()
                .id(null)
                .username(request.getUserName())
                .email(request.getUserEmail())
                .role(request.getUserRole())
                .build();
        userRepository.save(user);

        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .userRole(user.getRole())
                .userEmail(user.getEmail())
                .userTasks(new HashSet<>(user.getTasks()))
                .build();

        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(userResponse);





    }
}
