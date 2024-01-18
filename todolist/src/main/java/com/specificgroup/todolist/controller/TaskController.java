package com.specificgroup.todolist.controller;

import com.specificgroup.todolist.entity.Task;
import com.specificgroup.todolist.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This controller provide api for tasks
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/task")
public final class TaskController {
    private final TaskService taskService;

    /**
     * Validate provided id and trying to find entity by id 200(OK); if not then return 404(NOT FOUND)
     *
     * @param id id of entity
     * @return entity if exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskBy(@Valid @PathVariable Long id) {
//        log.info("input: " + id);
        Task task = taskService.getBy(id);
        return new ResponseEntity<>(task, HttpStatus.OK);

    }

    /**
     * Get all entities if nothing then return empty json with 200(OK)
     *
     * @return all entities
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAll();
//        log.info(tasks.toString());
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    /**
     * Validate provided task and groupId and trying to save entity 200(OK); if not then return 404(NOT FOUND)
     *
     * @param task    to be saved
     * @param groupId id of task's group
     * @return task with id
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task, @RequestParam Long groupId) {
//        log.info("input: " + task.toString());
        Task savedTask = taskService.save(task, groupId);
//        log.info("output: " + savedTask.toString());
        return new ResponseEntity<>(savedTask, HttpStatus.OK);

    }

    /**
     * Validate provided task and trying to update entity 200(OK); if not then return 404(NOT FOUND)
     *
     * @param task to be updated
     * @return updated task
     */
    @PutMapping
    public ResponseEntity<Task> updateTask(@Valid @RequestBody Task task) {
//        log.info("input: " + task.toString());
        Task updatedTask = taskService.update(task);
//            log.info("output: " + updatedTask.toString());
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    /**
     * Validate id of the task and trying to done task 200(OK); if not then return 404(NOT FOUND)
     *
     * @param id of done task
     * @return done task
     */
    @PutMapping("/done/{id}")
    public ResponseEntity<Task> doneTask(@Valid @PathVariable Long id) {
        Task doneTask = taskService.doneTask(id);
        return new ResponseEntity<>(doneTask, HttpStatus.OK);
    }


    /**
     * Validate id of the task and trying to delete task 204(NO CONTENT)
     *
     * @param id id of the task
     * @return empty json
     */
    @DeleteMapping
    public ResponseEntity<Task> deleteTaskBy(@Valid @RequestParam Long id) {
//        log.info("input: " + id);
        taskService.deleteBy(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
