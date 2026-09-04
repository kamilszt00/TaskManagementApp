package com.kamil.TaskManagement.controller;


import com.kamil.TaskManagement.DTO.CreateUserRequest;
import com.kamil.TaskManagement.DTO.UserResponse;
import com.kamil.TaskManagement.model.User;
import com.kamil.TaskManagement.repository.UserRepository;
import com.kamil.TaskManagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/user")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN','EMPLOYEE')")
    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/user/{id}")
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody CreateUserRequest userRequest,@PathVariable Integer id) {
        return userService.updateUser(userRequest,id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }






}
