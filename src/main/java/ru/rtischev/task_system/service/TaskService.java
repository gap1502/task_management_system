package ru.rtischev.task_system.service;

import ru.rtischev.task_system.dto.TaskDto;
import ru.rtischev.task_system.model.Task;
import ru.rtischev.task_system.model.User;

import java.util.Set;

public interface TaskService {

    Task createTask(TaskDto taskDto, User author);

    TaskDto updateTask(Long taskId, TaskDto taskDto);

    void deleteTask(Long taskId);

    TaskDto getTaskById(Long taskId);

    Set<TaskDto> getAllTasks();

}
