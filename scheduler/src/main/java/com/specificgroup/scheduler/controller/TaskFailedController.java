package com.specificgroup.scheduler.controller;

import com.specificgroup.scheduler.service.TaskFailedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller for searching manually failed tasks
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/scheduler/")
public class TaskFailedController {
    private final TaskFailedService service;

    @GetMapping
    public void checkFailedTasks() {
        service.getExpiredTasks();
    }
}
