package com.specificgroup.ratingservice.dto;

import com.specificgroup.ratingservice.domain.Status;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class TaskEvent {

    private UUID userId;
    private Long taskId;
    private Status status;
}
