package com.kamil.TaskManagement.config;

import com.kamil.TaskManagement.model.User;
import com.kamil.TaskManagement.model.UserRole;
import com.kamil.TaskManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${ADMIN_USERNAME:admin}")
    private String username;
    @Value("${ADMIN_EMAIL:admin@example.com}")
    private String email;
    @Value("${ADMIN_PASSWORD:}")
    private String password;

    @Override
    public void run(String... args) {
        if (userRepository.existsByRole(UserRole.ADMIN) || password.isBlank()) {
            return;
        }
        userRepository.save(User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(UserRole.ADMIN)
                .build());
    }
}
