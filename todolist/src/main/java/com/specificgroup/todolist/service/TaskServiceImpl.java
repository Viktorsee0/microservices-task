package com.specificgroup.todolist.service;

import com.specificgroup.todolist.entity.Status;
import com.specificgroup.todolist.entity.Task;
import com.specificgroup.todolist.entity.TaskDoneEvent;
import com.specificgroup.todolist.entity.TaskGroup;
import com.specificgroup.todolist.exception.NotFoundException;
import com.specificgroup.todolist.repository.TaskGroupRepository;
import com.specificgroup.todolist.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
final class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskGroupRepository taskGroupRepository;
    private final TaskDonePublisher taskDonePublisher;

    @Override
    public Task getBy(Long id) throws NotFoundException {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            return optionalTask.get();
        } else {
            throw new NotFoundException("No task with such id");
        }
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task save(Task task, Long groupId) throws NotFoundException {
        Optional<TaskGroup> optionalGroup = taskGroupRepository.findById(groupId);
        if (optionalGroup.isPresent()) {
            TaskGroup group = optionalGroup.get();
            task.setTaskGroup(group);
            return taskRepository.save(task);
        } else {
            throw new NotFoundException("no group with such id");
        }
    }

    @Override
    public Task update(Task updatedTask) throws NotFoundException {
        Optional<Task> optionalOldTask = taskRepository.findById(updatedTask.getId());
        if (optionalOldTask.isPresent()) {
            Task oldTask = optionalOldTask.get();

            String title = updatedTask.getTitle();
            if (title != null) {
                oldTask.setTitle(title);
            }

            String description = updatedTask.getDescription();
            if (description != null) {
                oldTask.setDescription(description);
            }

//            if (updatedTask.getStatus() != null) {
//                oldTask.setStatus(updatedTask.getStatus());
//            }

            return taskRepository.save(oldTask);
        } else {
            throw new NotFoundException("No entity with such id");
        }
    }

    @Override
    public Task doneTask(Long id) throws NotFoundException {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            if (Status.DONE == task.getStatus()) {
                return task;
            }
            task.setStatus(Status.DONE);

            TaskDoneEvent taskDoneEvent = new TaskDoneEvent(
                    task.getTaskGroup().getUserId(),
                    task.getId(),
                    task.getStatus());
            taskDonePublisher.taskDone(taskDoneEvent);

            return taskRepository.save(task);
        } else {
            throw new NotFoundException("No entity with such id");
        }
    }

    @Override
    public void deleteBy(Long id) {
        taskRepository.deleteById(id);
    }

}
