package com.specificgroup.todolist.repository;

import com.specificgroup.todolist.entity.TaskGroup;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskGroupRepository extends JpaRepository<TaskGroup, Long> {

    List<TaskGroup> findByUserId(UUID userId);
}
