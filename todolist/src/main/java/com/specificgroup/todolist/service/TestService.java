package com.specificgroup.todolist.service;

import com.specificgroup.todolist.entity.TaskDoneEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class TestService {
    @RabbitListener(queues = "${amqp.queue.task}")
    void handleMultiplicationSolved(final TaskDoneEvent event) {
        log.info("####################-----receive from mq: {}-----##########################", event);
    }
}
