package com.specificgroup.ratingservice.service;

import com.specificgroup.ratingservice.domain.Status;
import com.specificgroup.ratingservice.dto.TaskEvent;
import com.specificgroup.ratingservice.service.impl.TaskEventHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@EnableRabbit
class TaskEventHandlerTest {

    @InjectMocks
    private TaskEventHandler taskEventHandler;
    @Mock
    private UserScoreService userScoreService;
    @Captor
    private ArgumentCaptor<TaskEvent> eventCaptor;

    @Test
    @DisplayName("Should call score service")
    void handleMultiplicationSolved_shouldUpdateScoreForUser() {
        // given
        TaskEvent event = new TaskEvent(UUID.randomUUID(), 1L, Status.DONE);

        // when
        taskEventHandler.handleMultiplicationSolved(event);

        // then
        verify(userScoreService).updateScoreForUser(eventCaptor.capture());
        then(eventCaptor.getValue()).isEqualTo(event);
    }
}
