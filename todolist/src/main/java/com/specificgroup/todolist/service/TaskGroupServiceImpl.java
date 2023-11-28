package com.specificgroup.todolist.service;

import com.specificgroup.todolist.entity.Task;
import com.specificgroup.todolist.entity.TaskGroup;
import com.specificgroup.todolist.exception.NotFoundException;
import com.specificgroup.todolist.repository.TaskGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
final class TaskGroupServiceImpl implements TaskGroupService {

    private final TaskGroupRepository taskGroupRepository;

    @Override
    public TaskGroup getBy(Long id) throws NotFoundException {
        Optional<TaskGroup> optionalGroup = taskGroupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            return optionalGroup.get();
        } else {
            throw new NotFoundException("No group with such id");
        }
    }

    @Override
    public List<TaskGroup> getAll() {
        List<TaskGroup> all = taskGroupRepository.findAll();
        return all;
    }

    @Override
    public TaskGroup save(TaskGroup taskGroup) {
        TaskGroup save = taskGroupRepository.save(taskGroup);
        return save;
    }

    @Override
    public TaskGroup update(TaskGroup updatedGroup) throws NotFoundException {
        Optional<TaskGroup> optionalOldGroup = taskGroupRepository.findById(updatedGroup.getId());
        if (optionalOldGroup.isPresent()) {
            TaskGroup oldGroup = optionalOldGroup.get();

            String title = updatedGroup.getTitle();
            if (title != null) {
                oldGroup.setTitle(title);
            }

            List<Task> tasks = updatedGroup.getTasks();
            if (tasks != null) {
                oldGroup.setTasks(tasks);
            }

            TaskGroup updated = taskGroupRepository.save(oldGroup);
            return updated;
        } else {
            throw new NotFoundException("No group with such id");
        }
    }

    @Override
    public void deleteBy(Long id) {
        taskGroupRepository.deleteById(id);
    }

}
