package com.specificgroup.scheduler.service;

/**
 * Main service for failed tasks
 */
public interface TaskFailedService {
    /**
     * receiving expired tasks from source
     */
    void getExpiredTasks();

}
