package com.kamil.TaskManagement.exception;


import com.kamil.TaskManagement.DTO.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> EntityNotfound(EntityNotFoundException entityNotFoundException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(entityNotFoundException.getMessage())
                .status(404)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorResponse.getStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> dataIntViolation(DataIntegrityViolationException dataIntegrityViolationException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("One or more referenced entities do not exist, or this operation violates a data constraint.")
                .status(400)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> genericHandler(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("An unexpected Error occured")
                .status(500)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
}
