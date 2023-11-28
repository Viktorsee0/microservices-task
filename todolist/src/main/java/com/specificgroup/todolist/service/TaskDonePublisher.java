package com.specificgroup.todolist.service;

import com.specificgroup.todolist.entity.TaskDoneEvent;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * This class provides a function to send event via rabbitmq to another service
 */
@Service
final class TaskDonePublisher {
    private final AmqpTemplate amqpTemplate;
    private final String taskTopicExchange;

    public TaskDonePublisher(final AmqpTemplate amqpTemplate,
                             @Value("${amqp.exchange.task}") final String taskTopicExchange) {
        this.amqpTemplate = amqpTemplate;
        this.taskTopicExchange = taskTopicExchange;
    }

    /**
     * Serialize event and sent to another service
     *
     * @param taskDoneEvent event to be sent
     */
    public void taskDone(final TaskDoneEvent taskDoneEvent) {
        String routingKey = "task.done";
        amqpTemplate.convertAndSend(taskTopicExchange, routingKey, taskDoneEvent);
    }
}
