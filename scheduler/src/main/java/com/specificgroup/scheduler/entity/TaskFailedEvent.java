package com.specificgroup.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public final class TaskFailedEvent {
    private final UUID userId;
    private final Long taskId;
    private final Status status;

    @Override
    public String toString() {
        return "TaskFailedEvent{" +
                "userId=" + userId +
                ", taskId=" + taskId +
                ", status=" + status +
                '}';
    }
}
