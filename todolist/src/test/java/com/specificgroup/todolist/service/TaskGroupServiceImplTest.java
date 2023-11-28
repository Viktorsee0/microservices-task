package com.specificgroup.todolist.service;

import com.specificgroup.todolist.entity.TaskGroup;
import com.specificgroup.todolist.exception.NotFoundException;
import com.specificgroup.todolist.repository.TaskGroupRepository;
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
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TaskGroupServiceImplTest {
    @Mock
    private TaskGroupRepository taskGroupRepository;

    private TaskGroupService groupService;

    @BeforeEach
    public void init() {
        groupService = new TaskGroupServiceImpl(taskGroupRepository);
    }

    @Test
    void getByIdSuccessful() {
        //given
        Long id = 1L;

        TaskGroup expectedGroup = new TaskGroup();
        expectedGroup.setId(id);
        expectedGroup.setTitle("title");
        expectedGroup.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        given(taskGroupRepository.findById(anyLong())).willReturn(Optional.of(expectedGroup));

        //when
        TaskGroup group = groupService.getBy(id);

        //then
        then(group).isEqualTo(expectedGroup);
    }

    @Test
    void getByIdFailed() {
        //given
        Long id = 1L;

        given(taskGroupRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        Throwable thrown = catchThrowable(() -> groupService.getBy(id));

        //then
        then(thrown).isInstanceOf(NotFoundException.class);
    }

    @Test
    void getAllEmpty() {
        //given
        given(taskGroupRepository.findAll()).willReturn(List.of());
        //when
        List<TaskGroup> groups = groupService.getAll();

        //then
        then(groups)
                .isNotNull()
                .doesNotContainNull();
    }

    @Test
    void getAll() {
        //given
        TaskGroup group1 = new TaskGroup();
        TaskGroup group2 = new TaskGroup();
        List<TaskGroup> ExpectedGroups = List.of(group1, group2);

        given(taskGroupRepository.findAll()).willReturn(ExpectedGroups);

        //when
        List<TaskGroup> actualGroups = groupService.getAll();

        //then
        then(actualGroups)
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

        given(taskGroupRepository.save(group)).willReturn(group);

        //when
        TaskGroup actualGroup = groupService.save(group);

        //then
        then(actualGroup).isEqualTo(group);
    }

    @Test
    void updateSuccessful() {
        //given
        TaskGroup oldGroup = new TaskGroup();
        oldGroup.setId(1L);
        oldGroup.setTitle("old title");

        TaskGroup newGroup = new TaskGroup();
        newGroup.setId(1L);
        newGroup.setTitle("new title");

        given(taskGroupRepository.findById(newGroup.getId())).willReturn(Optional.of(oldGroup));
        given(taskGroupRepository.save(oldGroup)).willReturn(newGroup);


        //when
        TaskGroup actualTask = groupService.update(newGroup);

        //then
        then(actualTask).isEqualTo(newGroup);
    }

    @Test
    void updateFailed() {
        //given
        TaskGroup expectedGroup = new TaskGroup();
        expectedGroup.setId(0L);

        given(taskGroupRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> groupService.update(expectedGroup));

        //then
        then(throwable).isInstanceOf(NotFoundException.class);
    }

    @Test
    void deleteByIdSuccessful() {
        Long groupId = 1L;
        groupService.deleteBy(groupId);
        verify(taskGroupRepository).deleteById(groupId);
        verifyNoMoreInteractions(taskGroupRepository);
    }
}