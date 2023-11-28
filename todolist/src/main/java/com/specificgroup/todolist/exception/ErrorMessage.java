package com.specificgroup.todolist.exception;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ErrorMessage {
    private final int statusCode;
    private final Timestamp timestamp;
    private final String message;
    private final String description;

    public ErrorMessage(int statusCode, Timestamp timestamp, String message, String description) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.description = description;
    }

}
