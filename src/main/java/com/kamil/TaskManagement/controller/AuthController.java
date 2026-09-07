package com.kamil.TaskManagement.controller;


import com.kamil.TaskManagement.DTO.AuthRequest;
import com.kamil.TaskManagement.service.AuthService;
import com.kamil.TaskManagement.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;
    @PreAuthorize("permitAll()")
    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) {
        return authService.generateToken(authRequest);
    }
}
