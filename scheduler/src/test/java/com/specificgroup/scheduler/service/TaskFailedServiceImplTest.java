package com.specificgroup.scheduler.service;

import com.specificgroup.scheduler.entity.TaskFailedEvent;
import com.specificgroup.scheduler.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskFailedServiceImplTest {
    @Mock
    private TaskRepository repository;

    @Mock
    private TaskFailedPublisher publisher;

    @Mock
    private TaskFailedService service;

    @BeforeEach
    public void init() {
        service = new TaskFailedServiceImpl(repository, publisher);
    }

    @Test
    public void getExpiredTasks() {
        //given
        List<TaskFailedEvent> list = List.of();
        given(repository.findExpiredTasks()).willReturn(list);
        //when
        service.getExpiredTasks();

        //then
        verify(publisher, times(list.size())).taskFailed(any(TaskFailedEvent.class));
        verifyNoMoreInteractions(repository);
    }
}