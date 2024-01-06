package ru.rtischev.task_system.service;

import ru.rtischev.task_system.enums.TaskStatus;
import ru.rtischev.task_system.model.Task;

public interface UserService {
    void changeTaskStatus(Long taskId, TaskStatus newStatus);

    Task assignTask(Long taskId, String assigneeUsername);
}
