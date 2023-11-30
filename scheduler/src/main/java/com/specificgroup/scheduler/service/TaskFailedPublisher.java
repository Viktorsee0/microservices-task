package com.specificgroup.scheduler.service;

import com.specificgroup.scheduler.entity.TaskFailedEvent;

/**
 * Publisher for tasks
 */
public interface TaskFailedPublisher {
    /**
     * Updating task to failed and sending event
     *
     * @param event event that should be sent for receivers
     */
    void taskFailed(TaskFailedEvent event);

}
