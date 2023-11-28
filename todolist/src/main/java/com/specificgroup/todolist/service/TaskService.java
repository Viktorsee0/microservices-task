package com.specificgroup.todolist.service;

import com.specificgroup.todolist.entity.Task;
import com.specificgroup.todolist.exception.NotFoundException;

import java.util.List;

/**
 * Provide crud for Task entity
 */
public interface TaskService {
    /**
     * @param id of desired entity
     * @return desired entity
     * @throws NotFoundException when entity doesn't exist
     */
    Task getBy(Long id) throws NotFoundException;

    /**
     * @return all entites, if there is nothing - empty list
     */
    List<Task> getAll();

    /**
     * @param task    entity need to be saved
     * @param groupId id of group of task
     * @return this entity with id
     * @throws NotFoundException when group with such id doesn't exist
     */
    Task save(Task task, Long groupId) throws NotFoundException;
    /**
     * @param task entity with (partly)updated fields
     * @return updated entity
     * @throws NotFoundException when invalid id of param entity
     */
    Task update(Task task) throws NotFoundException;

    /**
     * @param id of done task
     * @return done task
     * @throws NotFoundException when no entity with such id
     */
    Task doneTask(Long id) throws NotFoundException;
    /**
     * @param id of deletable entity
     */
    void deleteBy(Long id);

}
