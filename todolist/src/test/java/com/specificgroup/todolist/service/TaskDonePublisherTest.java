package com.specificgroup.todolist.service;

import com.specificgroup.todolist.entity.Status;
import com.specificgroup.todolist.entity.TaskDoneEvent;
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
class TaskDonePublisherTest {
    @Mock
    private AmqpTemplate amqpTemplate;

    private TaskDonePublisher publisher;

    @BeforeEach
    public void init() {
        publisher = new TaskDonePublisher(amqpTemplate, "test.topic");
    }

    @Test
    void taskDone() {
        //given
        TaskDoneEvent taskDoneEvent = new TaskDoneEvent(new UUID(1L, 1L), 1L, Status.DONE);

        //when
        publisher.taskDone(taskDoneEvent);

        //then
        var exchangeCaptor = ArgumentCaptor.forClass(String.class);
        var routingKeyCaptor = ArgumentCaptor.forClass(String.class);
        var eventCaptor = ArgumentCaptor.forClass(TaskDoneEvent.class);

        verify(amqpTemplate).convertAndSend(exchangeCaptor.capture(), routingKeyCaptor.capture(), eventCaptor.capture());
        then(exchangeCaptor.getValue()).isEqualTo("test.topic");
        then(routingKeyCaptor.getValue()).isEqualTo("task.done");
        then(eventCaptor.getValue()).isEqualTo(taskDoneEvent);
    }
}