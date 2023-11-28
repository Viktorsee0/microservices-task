package com.specificgroup.todolist.service;


import com.specificgroup.todolist.entity.TaskGroup;
import com.specificgroup.todolist.exception.NotFoundException;

import java.util.List;

/**
 * Provide crud for TaskGroup entity
 */
public interface TaskGroupService {

    /**
     * @param id of desired entity
     * @return desired entity
     * @throws NotFoundException when entity doesn't exist
     */
    TaskGroup getBy(Long id) throws NotFoundException;

    /**
     * @return all entites, if there is nothing - empty list
     */
    List<TaskGroup> getAll();

    /**
     * @param taskGroup entity need to be saved
     * @return this entity with id
     */
    TaskGroup save(TaskGroup taskGroup);

    /**
     * @param taskGroup entity with (partly)updated fields
     * @return updated entity
     * @throws NotFoundException when invalid id of param entity
     */
    TaskGroup update(TaskGroup taskGroup) throws NotFoundException;

    /**
     * @param id of deletable entity
     */
    void deleteBy(Long id);

}
