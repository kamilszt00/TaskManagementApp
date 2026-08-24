package com.kamil.TaskManagement.service;


import com.kamil.TaskManagement.DTO.CreateUserRequest;
import com.kamil.TaskManagement.DTO.UserResponse;
import com.kamil.TaskManagement.mapper.UserMapper;
import com.kamil.TaskManagement.model.User;
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
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public ResponseEntity<UserResponse> createUser(CreateUserRequest request) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(userMapper.toResponse(userRepository.save(userMapper.toRequest(request))));
    }

    public ResponseEntity<UserResponse> getUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(user -> ResponseEntity.ok(userMapper.toResponse(user))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<UserResponse> updateUser(CreateUserRequest userRequest, Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = userMapper.toRequest(userRequest);
            user.setId(optionalUser.get().getId());
            return  ResponseEntity.ok(userMapper.toResponse(userRepository.save(user)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<UserResponse> deleteUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
