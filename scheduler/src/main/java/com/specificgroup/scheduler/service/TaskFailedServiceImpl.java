package com.specificgroup.scheduler.service;

import com.specificgroup.scheduler.entity.TaskFailedEvent;
import com.specificgroup.scheduler.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
@EnableScheduling
final class TaskFailedServiceImpl implements TaskFailedService {
    private final TaskRepository repository;
    private final TaskFailedPublisher publisher;

    @Scheduled(fixedDelay = 30000)
    public void getExpiredTasks() {
        List<TaskFailedEvent> expired = repository.findExpiredTasks();
        log.info("list of events with failed tasks: {}", expired);
        for (TaskFailedEvent event : expired) {
            publisher.taskFailed(event);
        }
    }
}
