package com.specificgroup.todolist.service;

import com.specificgroup.todolist.entity.Status;
import com.specificgroup.todolist.entity.Task;
import com.specificgroup.todolist.entity.TaskGroup;
import com.specificgroup.todolist.exception.NotFoundException;
import com.specificgroup.todolist.repository.TaskGroupRepository;
import com.specificgroup.todolist.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskGroupRepository taskGroupRepository;
    @Mock
    private TaskDonePublisher taskDonePublisher;

    private TaskService taskService;

    @BeforeEach
    public void init() {
        taskService = new TaskServiceImpl(taskRepository, taskGroupRepository, taskDonePublisher);
    }

    @Test
    void getByIdSuccessful() {
        //given
        Long id = 1L;

        Task expectedTask = new Task();
        expectedTask.setId(id);
        expectedTask.setTitle("title");
        expectedTask.setTaskGroup(new TaskGroup());
        expectedTask.setDescription("description");
        expectedTask.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        expectedTask.setExpireAt(Timestamp.valueOf(LocalDateTime.now().plusDays(1)));

        given(taskRepository.findById(any(Long.class))).willReturn(Optional.of(expectedTask));

        //when
        Task task = taskService.getBy(id);

        //then
        then(task).isEqualTo(expectedTask);
    }

    @Test
    void getByIdFailed() {
        //given
        Long id = 1L;

        given(taskRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        Throwable thrown = catchThrowable(() -> taskService.getBy(id));

        //then
        then(thrown).isInstanceOf(NotFoundException.class);
    }

    @Test
    void getAllEmpty() {
        //given
        given(taskRepository.findAll()).willReturn(List.of());
        //when
        List<Task> tasks = taskService.getAll();

        //then
        then(tasks)
                .isNotNull()
                .doesNotContainNull();
    }

    @Test
    void getAll() {
        //given
        Task task1 = new Task();
        Task task2 = new Task();
        List<Task> ExpectedTasks = List.of(task1, task2);

        given(taskRepository.findAll()).willReturn(ExpectedTasks);

        //when
        List<Task> ActualTasks = taskService.getAll();

        //then
        then(ActualTasks)
                .isNotNull()
                .doesNotContainNull()
                .hasSize(2);
    }

    @Test
    void saveSuccessful() {
        //given
        Long groupId = 1L;
        TaskGroup group = new TaskGroup();
        group.setId(groupId);
        Task expectedTask = new Task();
        expectedTask.setTaskGroup(group);

        given(taskGroupRepository.findById(groupId)).willReturn(Optional.of(group));
        given(taskRepository.save(expectedTask)).willReturn(expectedTask);

        //when
        Task actualTask = taskService.save(expectedTask, groupId);

        //then
        then(actualTask).isEqualTo(expectedTask);
    }

    @Test
    void saveFailed() {
        //given
        Long groupId = 1L;
        TaskGroup group = new TaskGroup();
        group.setId(groupId);
        Task expectedTask = new Task();
        expectedTask.setTaskGroup(group);

        given(taskGroupRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> taskService.save(expectedTask, groupId));

        //then
        then(throwable).isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateSuccessful() {
        //given
        Task oldTask = new Task();
        oldTask.setId(1L);
        oldTask.setTitle("old title");
        oldTask.setDescription("old description");

        Task newTask = new Task();
        newTask.setId(1L);
        newTask.setTitle("new title");
        newTask.setDescription("new description");

        given(taskRepository.findById(newTask.getId())).willReturn(Optional.of(oldTask));
        given(taskRepository.save(oldTask)).willReturn(newTask);


        //when
        Task actualTask = taskService.update(newTask);

        //then
        then(actualTask).isEqualTo(newTask);
    }

    @Test
    void updateFailed() {
        //given
        Long groupId = 1L;
        TaskGroup group = new TaskGroup();
        group.setId(groupId);
        Task expectedTask = new Task();
        expectedTask.setTaskGroup(group);
        expectedTask.setId(0L);

        given(taskRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> taskService.update(expectedTask));

        //then
        then(throwable).isInstanceOf(NotFoundException.class);
    }

    @Test
    void doneTaskSuccessful() {
        //given

        TaskGroup group = new TaskGroup();
        group.setId(1L);
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        task.setTaskGroup(group);
        task.setStatus(Status.IN_PROGRESS);

        given(taskRepository.findById(taskId)).willReturn(Optional.of(task));
        given(taskRepository.save(task)).willReturn(task);

        //when
        Task donedTask = taskService.doneTask(taskId);

        //then
        then(donedTask).isEqualTo(task);
    }

    @Test
    void doneTaskFailed() {
        //given
        Long taskId = 0L;
        given(taskRepository.findById(taskId)).willReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> taskService.doneTask(taskId));

        //then
        then(throwable).isInstanceOf(NotFoundException.class);
    }

    @Test
    void deleteByIdSuccessful() {
        Long taskId = 1L;
        taskService.deleteBy(taskId);
        verify(taskRepository).deleteById(taskId);
        verifyNoMoreInteractions(taskRepository);
    }
}