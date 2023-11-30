package com.specificgroup.scheduler.service;

import com.specificgroup.scheduler.entity.Status;
import com.specificgroup.scheduler.entity.TaskFailedEvent;
import com.specificgroup.scheduler.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TaskFailedPublisherImplTest {
    @Mock
    private AmqpTemplate amqpTemplate;
    @Mock
    private TaskRepository repository;
    private TaskFailedPublisher publisher;


    @BeforeEach
    public void init() {
        publisher = new TaskFailedPublisherImpl(amqpTemplate, "test.topic", repository);
    }

    @Test
    void taskDone() {
        //given
        TaskFailedEvent taskFailedEvent = new TaskFailedEvent(new UUID(1L, 1L), 1L, Status.FAILED);

        //when
        publisher.taskFailed(taskFailedEvent);

        //then
        var exchangeCaptor = ArgumentCaptor.forClass(String.class);
        var routingKeyCaptor = ArgumentCaptor.forClass(String.class);
        var eventCaptor = ArgumentCaptor.forClass(TaskFailedEvent.class);

        verify(amqpTemplate).convertAndSend(exchangeCaptor.capture(), routingKeyCaptor.capture(), eventCaptor.capture());
        then(exchangeCaptor.getValue()).isEqualTo("test.topic");
        then(routingKeyCaptor.getValue()).isEqualTo("task.failed");
        then(eventCaptor.getValue()).isEqualTo(taskFailedEvent);
    }
}