package ru.rtischev.task_system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.rtischev.task_system.dto.CommentDto;
import ru.rtischev.task_system.dto.TaskDto;
import ru.rtischev.task_system.exceptions.TaskNotFoundException;
import ru.rtischev.task_system.exceptions.UnauthorizedUserException;
import ru.rtischev.task_system.model.Comment;
import ru.rtischev.task_system.model.Task;
import ru.rtischev.task_system.model.User;
import ru.rtischev.task_system.repository.TaskRepository;
import ru.rtischev.task_system.repository.UserRepository;
import ru.rtischev.task_system.service.CurrentUserDetailsService;
import ru.rtischev.task_system.service.TaskService;

import java.util.Set;
import java.util.stream.Collectors;


@Component
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private final CurrentUserDetailsService currentUserDetailsService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, CurrentUserDetailsService currentUserDetailsService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.currentUserDetailsService = currentUserDetailsService;
    }


    @Override
    public Task createTask(TaskDto taskDto, User author) {
        User currentUser = currentUserDetailsService.getCurrentUser();
        if (currentUser != null) {
            Task task = new Task();
            task.setTitle(taskDto.getTitle());
            task.setDescription(taskDto.getDescription());
            task.setStatus(taskDto.getStatus());
            task.setPriority(taskDto.getPriority());
            task.setAuthor(currentUser);

            return taskRepository.save(task);
        } else {

            throw new UnauthorizedUserException("Current user not found");
        }
    }

    @Override
    public TaskDto updateTask(Long taskId, TaskDto taskDto) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        existingTask.setTitle(taskDto.getTitle());
        existingTask.setDescription(taskDto.getDescription());
        existingTask.setStatus(taskDto.getStatus());
        existingTask.setPriority(taskDto.getPriority());

        if (taskDto.getAssigneeId() != null) {
            User assignee = userRepository.findById(taskDto.getAssigneeId())
                    .orElseThrow(() -> new TaskNotFoundException("User not found with id: " + taskDto.getAssigneeId()));
            existingTask.setAssignee(assignee);
        }
        taskRepository.save(existingTask);
        return convertTaskToDto(existingTask);
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId) {
        if (taskRepository.findById(taskId).isEmpty()) {
            throw new TaskNotFoundException("Task not found with ID: " + taskId);
        }

        taskRepository.deleteById(taskId);
    }

    @Override
    public TaskDto getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
        return convertTaskToDto(task);
    }

    @Override
    public Set<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::convertTaskToDto)
                .collect(Collectors.toSet());
    }


    private TaskDto convertTaskToDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        taskDto.setPriority(task.getPriority());

        if (task.getAuthor() != null) {
            taskDto.setAuthorId(task.getAuthor().getId());
        }

        if (task.getAssignee() != null) {
            taskDto.setAssigneeId(task.getAssignee().getId());
        }

        Set<CommentDto> commentDtos = task.getComments().stream()
                .map(this::convertCommentToDto)
                .collect(Collectors.toSet());

        taskDto.setComments(commentDtos);

        return taskDto;
    }

    private CommentDto convertCommentToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());

        if (comment.getTask() != null) {
            commentDto.setTaskId(comment.getTask().getId());
        }

        if (comment.getUser() != null) {
            commentDto.setUserId(comment.getUser().getId());
        }

        commentDto.setContent(comment.getContent());
        commentDto.setCreatedAt(comment.getCreatedAt());

        return commentDto;
    }

}
