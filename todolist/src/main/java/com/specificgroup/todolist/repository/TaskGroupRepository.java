package com.specificgroup.todolist.repository;

import com.specificgroup.todolist.entity.TaskGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskGroupRepository extends JpaRepository<TaskGroup, Long> {
}
