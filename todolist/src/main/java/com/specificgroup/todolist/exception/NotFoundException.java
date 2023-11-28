package com.specificgroup.todolist.exception;

public final class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
