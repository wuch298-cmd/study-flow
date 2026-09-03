package com.wuch298.studyflow.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Task not found: "+id);
    }
}
