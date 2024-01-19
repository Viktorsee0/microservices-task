package com.specificgroup.todolist.controller;

import com.specificgroup.todolist.entity.TaskGroup;
import com.specificgroup.todolist.service.TaskGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * * This controller provide api for groups
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/task-group")
public final class TaskGroupController {
    private final TaskGroupService taskGroupService;

    /**
     * Validate provided id and trying to find entity by id 200(OK); if not then return 404(NOT FOUND)
     *
     * @param id id of entity
     * @return entity if exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskGroup> getGroupBy(@Valid @PathVariable Long id) {
        log.info("input: " + id);
        TaskGroup group = taskGroupService.getBy(id);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @GetMapping("/user-groups/{userId}")
    public ResponseEntity<List<TaskGroup>> getGroupsByUserId(@Valid @PathVariable UUID userId) {
        log.info("input: " + userId);
        List<TaskGroup> groups = taskGroupService.getByUserId(userId);
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    /**
     * Get all entities if nothing then return empty json with 200(OK)
     *
     * @return all entities
     */
    @GetMapping
    public ResponseEntity<List<TaskGroup>> getAllGroups() {
        List<TaskGroup> groups = taskGroupService.getAll();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    /**
     * Validate provided group and trying to save entity 200(OK); if not then return 404(NOT FOUND)
     *
     * @param group to be saved
     * @return group with id
     */
    @PostMapping
    public ResponseEntity<TaskGroup> createGroup(@Valid @RequestBody TaskGroup group) {
        TaskGroup savedGroup = taskGroupService.save(group);
        return new ResponseEntity<>(savedGroup, HttpStatus.OK);
    }

    /**
     * Validate provided group and trying to update entity 200(OK); if not then return 404(NOT FOUND)
     *
     * @param group to be updated
     * @return updated group
     */
    @PutMapping
    public ResponseEntity<TaskGroup> updateGroup(@Valid @RequestBody TaskGroup group) {
        TaskGroup updatedGroup = taskGroupService.update(group);
        return new ResponseEntity<>(updatedGroup, HttpStatus.OK);
    }

    /**
     * Validate id of the group and trying to delete group 204(NO CONTENT)
     *
     * @param id id of the group
     * @return empty json
     */
    @DeleteMapping
    public ResponseEntity<TaskGroup> deleteGroupBy(@Valid @RequestParam Long id) {
        log.info("input: " + id);
        taskGroupService.deleteBy(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
