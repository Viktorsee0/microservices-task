package com.specificgroup.todolist.controller;

import com.specificgroup.todolist.entity.Status;
import com.specificgroup.todolist.entity.Task;
import com.specificgroup.todolist.entity.TaskGroup;
import com.specificgroup.todolist.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @MockBean
    private TaskService taskService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<Task> taskJacksonTester;
    @Autowired
    private JacksonTester<List<Task>> listTaskJacksonTester;

    @Test
    void getTaskById() throws Exception {
        // given
        Task expectedResponse = new Task(
                1L,
                "test title",
                "test description",
                Timestamp.valueOf(LocalDateTime.now().plusDays(1)),
                Status.IN_PROGRESS,
                new TaskGroup(),
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now())
        );

        given(taskService.getBy(anyLong())).willReturn(expectedResponse);

        // when
        MockHttpServletResponse response = mvc.perform(get("/api/v1/task/1")).andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(taskJacksonTester.write(expectedResponse).getJson());
    }

    @Test
    void getAllTasks() throws Exception {
        // given
        Task task1 = new Task(
                1L,
                "test title",
                "test description",
                Timestamp.valueOf(LocalDateTime.now().plusDays(1)),
                Status.IN_PROGRESS,
                new TaskGroup(),
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now())
        );
        Task task2 = new Task(
                2L,
                "test title",
                "test description",
                Timestamp.valueOf(LocalDateTime.now().plusDays(1)),
                Status.IN_PROGRESS,
                new TaskGroup(),
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now())
        );
        List<Task> expectedTasks = List.of(task1, task2);

        given(taskService.getAll()).willReturn(expectedTasks);

        // when
        MockHttpServletResponse response = mvc.perform(get("/api/v1/task")).andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(listTaskJacksonTester.write(expectedTasks).getJson());
    }

    @Test
    void createTask() throws Exception {
        //given
        Task task = new Task(
                null,
                "test title",
                "test description",
                Timestamp.valueOf(LocalDateTime.now().plusDays(1)),
                Status.IN_PROGRESS,
                new TaskGroup(),
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now())
        );
        Task expectedResponse = new Task(
                1L,
                "test title",
                "test description",
                Timestamp.valueOf(LocalDateTime.now().plusDays(1)),
                Status.IN_PROGRESS,
                new TaskGroup(),
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now())
        );

        given(taskService.save(any(Task.class), anyLong())).willReturn(expectedResponse);

        //when
        MockHttpServletResponse response = mvc
                .perform(post("/api/v1/task?groupId=1").contentType(MediaType.APPLICATION_JSON).content(taskJacksonTester.write(task).getJson()))
                .andReturn()
                .getResponse();

        //then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(taskJacksonTester.write(expectedResponse).getJson());
    }

    @Test
    void updateTask() throws Exception {
        //given
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        Timestamp deadLine = Timestamp.valueOf(LocalDateTime.now().plusDays(1));
        Task task = new Task(
                1L,
                "test title",
                "test description",
                deadLine,
                Status.IN_PROGRESS,
                new TaskGroup(),
                timestamp,
                Timestamp.valueOf(LocalDateTime.now())
        );
        Task expectedResponse = new Task(
                1L,
                "new test title",
                "new test description",
                deadLine,
                Status.IN_PROGRESS,
                new TaskGroup(),
                timestamp,
                Timestamp.valueOf(LocalDateTime.now())
        );

        given(taskService.update(any(Task.class))).willReturn(expectedResponse);

        //when
        MockHttpServletResponse response = mvc
                .perform(put("/api/v1/task").contentType(MediaType.APPLICATION_JSON).content(taskJacksonTester.write(task).getJson()))
                .andReturn()
                .getResponse();

        //then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(taskJacksonTester.write(expectedResponse).getJson());
    }

    @Test
    void doneTask() throws Exception {
        //given
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        Timestamp deadLine = Timestamp.valueOf(LocalDateTime.now().plusDays(1));

        Task expectedResponse = new Task(
                1L,
                "test title",
                "test description",
                deadLine,
                Status.DONE,
                new TaskGroup(),
                timestamp,
                Timestamp.valueOf(LocalDateTime.now())
        );

        given(taskService.doneTask(anyLong())).willReturn(expectedResponse);

        //when
        MockHttpServletResponse response = mvc
                .perform(put("/api/v1/task/done/1").contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(taskJacksonTester.write(expectedResponse).getJson());
    }


    @Test
    void deleteTaskById() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                        delete("/api/v1/task?id=1"))
                .andReturn().getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

}