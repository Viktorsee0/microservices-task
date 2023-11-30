package com.specificgroup.scheduler.service;

import com.specificgroup.scheduler.entity.Status;
import com.specificgroup.scheduler.entity.TaskFailedEvent;
import com.specificgroup.scheduler.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class provides a function to send event via rabbitmq to another service
 */
@Slf4j
@Service
class TaskFailedPublisherImpl implements TaskFailedPublisher {
    private final AmqpTemplate amqpTemplate;
    private final String taskTopicExchange;
    private final TaskRepository taskRepository;

    public TaskFailedPublisherImpl(final AmqpTemplate amqpTemplate,
                                   @Value("${amqp.exchange.task}") final String taskTopicExchange, final TaskRepository taskRepository) {
        this.amqpTemplate = amqpTemplate;
        this.taskTopicExchange = taskTopicExchange;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public void taskFailed(TaskFailedEvent event) {
        //normally should equal to 1
        int i = taskRepository.setTaskToFailed(event.getTaskId());
        log.info("amount of updated raws: {}", i);

        TaskFailedEvent taskFailedEvent = new TaskFailedEvent(
                event.getUserId(),
                event.getTaskId(),
                Status.FAILED);
        log.info("failed task event: {}", taskFailedEvent);

        String routingKey = "task.failed";
        amqpTemplate.convertAndSend(taskTopicExchange, routingKey, taskFailedEvent);
    }
}
