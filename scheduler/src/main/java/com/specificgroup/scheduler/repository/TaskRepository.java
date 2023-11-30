package com.specificgroup.scheduler.repository;

import com.specificgroup.scheduler.entity.Task;
import com.specificgroup.scheduler.entity.TaskFailedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository for working with todolist db
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
    /**
     * Trying to find tasks with status IN_PROGRESS and expired deadline
     *
     * @return list of expired tasks
     */
    @Query("SELECT NEW com.specificgroup.scheduler.entity.TaskFailedEvent(g.userId, t.id, t.status) " +
            "FROM com.specificgroup.scheduler.entity.Task AS t " +
            "JOIN com.specificgroup.scheduler.entity.TaskGroup AS g ON t.taskGroup.id = g.id " +
            "WHERE t.status='IN_PROGRESS' AND t.expireAt <= CURRENT_TIMESTAMP ")
    List<TaskFailedEvent> findExpiredTasks();

    /**
     * @param taskId id of failed task
     * @return count of failed rows
     */
    @Modifying
    @Query(value = "UPDATE task SET task_status = 'FAILED' WHERE task_id = ?", nativeQuery = true)
    int setTaskToFailed(Long taskId);
}
