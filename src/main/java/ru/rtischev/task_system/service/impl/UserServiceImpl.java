package ru.rtischev.task_system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rtischev.task_system.enums.TaskStatus;
import ru.rtischev.task_system.exceptions.TaskNotFoundException;
import ru.rtischev.task_system.exceptions.UnauthorizedUserException;
import ru.rtischev.task_system.model.Task;
import ru.rtischev.task_system.model.User;
import ru.rtischev.task_system.repository.TaskRepository;
import ru.rtischev.task_system.repository.UserRepository;
import ru.rtischev.task_system.service.UserService;

@Component
public class UserServiceImpl implements UserService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void changeTaskStatus(Long taskId, TaskStatus newStatus) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        task.setStatus(newStatus);
        taskRepository.save(task);
    }

    @Override
    public Task assignTask(Long taskId, String assigneeUsername) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        User assignee = userRepository.findByUsername(assigneeUsername)
                .orElseThrow(() -> new UnauthorizedUserException("User not found with username: " + assigneeUsername));

        task.setAssignee(assignee);
        return taskRepository.save(task);
    }
}
