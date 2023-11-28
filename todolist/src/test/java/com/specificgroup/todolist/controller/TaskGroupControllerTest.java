package com.specificgroup.todolist.controller;

import com.specificgroup.todolist.entity.Task;
import com.specificgroup.todolist.entity.TaskGroup;
import com.specificgroup.todolist.service.TaskGroupService;
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
import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(TaskGroupController.class)
class TaskGroupControllerTest {

    @MockBean
    private TaskGroupService taskGroupService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<TaskGroup> groupJacksonTester;

    @Autowired
    private JacksonTester<List<TaskGroup>> listGroupJacksonTester;

    @Test
    void getGroupById() throws Exception {
        // given
        List<Task> tasks = List.of();
        TaskGroup expectedResponse = new TaskGroup(
                1L,
                new UUID(1L, 1L),
                "test title",
                tasks,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now())
        );

        given(taskGroupService.getBy(anyLong())).willReturn(expectedResponse);

        // when
        MockHttpServletResponse response = mvc.perform(get("/api/v1/task-group/1")).andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(groupJacksonTester.write(expectedResponse).getJson());
    }

    @Test
    void getAllGroups() throws Exception {
        // given
        List<Task> tasks = List.of();
        TaskGroup group1 = new TaskGroup(
                1L,
                new UUID(1L, 1L),
                "test title",
                tasks,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now())
        );
        TaskGroup group2 = new TaskGroup(
                2L,
                new UUID(1L, 1L),
                "test title",
                tasks,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now())
        );
        List<TaskGroup> expectedGroups = List.of(group1, group2);

        given(taskGroupService.getAll()).willReturn(expectedGroups);

        // when
        MockHttpServletResponse response = mvc.perform(get("/api/v1/task-group/")).andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(listGroupJacksonTester.write(expectedGroups).getJson());
    }

    @Test
    void createGroup() throws Exception {
        //given
        List<Task> tasks = List.of();
        TaskGroup group = new TaskGroup(
                null,
                new UUID(1L, 1L),
                "test title",
                tasks,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now())
        );
        TaskGroup expectedResponse = new TaskGroup(
                1L,
                new UUID(1L, 1L),
                "test title",
                tasks,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now())
        );

        given(taskGroupService.save(any(TaskGroup.class))).willReturn(expectedResponse);

        //when
        MockHttpServletResponse response = mvc
                .perform(post("/api/v1/task-group/").contentType(MediaType.APPLICATION_JSON).content(groupJacksonTester.write(group).getJson()))
                .andReturn()
                .getResponse();

        //then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(groupJacksonTester.write(expectedResponse).getJson());
    }

    @Test
    void updateGroup() throws Exception {
        //given
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        List<Task> tasks = List.of();
        TaskGroup group = new TaskGroup(
                1L,
                new UUID(1L, 1L),
                "test title",
                tasks,
                timestamp,
                Timestamp.valueOf(LocalDateTime.now())
        );
        TaskGroup expectedResponse = new TaskGroup(
                1L,
                new UUID(1L, 1L),
                "new test title",
                tasks,
                timestamp,
                Timestamp.valueOf(LocalDateTime.now())
        );

        given(taskGroupService.update(any(TaskGroup.class))).willReturn(expectedResponse);

        //when
        MockHttpServletResponse response = mvc
                .perform(put("/api/v1/task-group/").contentType(MediaType.APPLICATION_JSON).content(groupJacksonTester.write(group).getJson()))
                .andReturn()
                .getResponse();

        //then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(groupJacksonTester.write(expectedResponse).getJson());
    }

    @Test
    void deleteGroupById() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                        delete("/api/v1/task-group/?id=1"))
                .andReturn().getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}