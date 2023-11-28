package com.specificgroup.todolist.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public final class TaskDoneEvent {
    private final UUID userId;
    private final Long taskId;
    private final Status status;
}
