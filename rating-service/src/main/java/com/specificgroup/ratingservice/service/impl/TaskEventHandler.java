package com.specificgroup.ratingservice.service.impl;

import com.specificgroup.ratingservice.dto.TaskEvent;
import com.specificgroup.ratingservice.service.UserScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class TaskEventHandler {

    private final UserScoreService service;

    @RabbitListener(queues = "${amqp.queue.task}")
    public void handleMultiplicationSolved(final TaskEvent event) {
        log.info("Challenge Solved Event received: {}", event);
        try {
            service.updateScoreForUser(event);
        } catch (final Exception e) {
            log.error("Error when trying to process ChallengeSolvedEvent", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}