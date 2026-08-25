package com.kamil.TaskManagement.service;


import com.kamil.TaskManagement.DTO.CreateUserRequest;
import com.kamil.TaskManagement.DTO.UserResponse;
import com.kamil.TaskManagement.mapper.UserMapper;
import com.kamil.TaskManagement.model.User;
import com.kamil.TaskManagement.repository.TaskRepository;
import com.kamil.TaskManagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public ResponseEntity<UserResponse> createUser(CreateUserRequest request) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(userMapper.toResponse(userRepository.save(userMapper.toRequest(request))));
    }

    public ResponseEntity<UserResponse> getUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    public ResponseEntity<UserResponse> updateUser(CreateUserRequest userRequest, Integer id) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
            User user = userMapper.toRequest(userRequest);
            user.setId(userToUpdate.getId());
            return  ResponseEntity.ok(userMapper.toResponse(userRepository.save(user)));
    }

    public ResponseEntity<UserResponse> deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
            userRepository.delete(user);
            return ResponseEntity.noContent().build();
    }
}
