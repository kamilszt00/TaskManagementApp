package com.kamil.TaskManagement.exception;

public class InvalidUserRoleException extends RuntimeException {
    public InvalidUserRoleException(String message) {
        super(message);
    }
}
