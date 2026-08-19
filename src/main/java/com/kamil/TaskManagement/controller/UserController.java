package com.kamil.TaskManagement.controller;


import com.kamil.TaskManagement.model.User;
import com.kamil.TaskManagement.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> viewUsers(@RequestParam int id_before,@RequestParam int id_after) {
        return userRepository.findAllByIdBetween(id_before,id_after);
    }

}
