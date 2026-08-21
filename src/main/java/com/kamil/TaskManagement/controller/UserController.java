package com.kamil.TaskManagement.controller;


import com.kamil.TaskManagement.DTO.CreateUserRequest;
import com.kamil.TaskManagement.DTO.UserResponse;
import com.kamil.TaskManagement.model.User;
import com.kamil.TaskManagement.repository.UserRepository;
import com.kamil.TaskManagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/user")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }


//    @PutMapping
//
//
//    @DeleteMapping






}
