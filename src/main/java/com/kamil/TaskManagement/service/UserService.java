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

import java.util.Optional;

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
                .userTasks(taskRepository.getTaskNamesFromUser(user.getId()))
                .build();

        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(userResponse);





    }

    public ResponseEntity<UserResponse> getUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseEntity.ok(
                            UserResponse.builder()
                            .id(user.getId())
                            .userName(user.getUsername())
                            .userRole(user.getRole())
                            .userEmail(user.getEmail())
                            .userTasks(taskRepository.getTaskNamesFromUser(user.getId()))
                            .build()
            );
        } else {
            return ResponseEntity.notFound().build();

        }




    }

    public ResponseEntity<UserResponse> updateUser(CreateUserRequest userRequest, Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRole(userRequest.getUserRole());
            user.setEmail(userRequest.getUserEmail());
            user.setUsername(userRequest.getUserName());
            userRepository.save(user);


            return  ResponseEntity.ok( UserResponse.builder()
                    .id(user.getId())
                    .userName(user.getUsername())
                    .userRole(user.getRole())
                    .userEmail(user.getEmail())
                    .userTasks(taskRepository.getTaskNamesFromUser(user.getId()))
                    .build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
