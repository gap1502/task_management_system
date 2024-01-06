package ru.rtischev.task_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rtischev.task_system.enums.TaskStatus;
import ru.rtischev.task_system.model.Task;
import ru.rtischev.task_system.model.User;
import ru.rtischev.task_system.repository.TaskRepository;
import ru.rtischev.task_system.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Система лояльности", description = "Статус и исполнитель")
public class UserController {

    private final UserService userService;
    private final TaskRepository taskRepository;

    @Autowired
    public UserController(UserService userService, TaskRepository taskRepository) {
        this.userService = userService;
        this.taskRepository = taskRepository;
    }

    @Operation(
            summary = "обновление статуса задачи"
    )
    @SecurityRequirement(name = "JWT")
    @PutMapping("/{taskId}/status")
    public ResponseEntity<Void> changeTaskStatus(
            @PathVariable Long taskId,
            @RequestParam TaskStatus newStatus) {
        userService.changeTaskStatus(taskId, newStatus);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "получение исполнителя задачи"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{taskId}/assignee")
    public ResponseEntity<User> getAssigneeByTaskId(@PathVariable Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);

        if (task.isPresent()) {
            Optional<User> assignee = Optional.ofNullable(task.get().getAssignee());
            return assignee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "добавление исполнителя к задачи"
    )
    @SecurityRequirement(name = "JWT")
    @PutMapping("/{taskId}/assign")
    public ResponseEntity<Task> assignTask(@PathVariable Long taskId, @RequestParam String assigneeUsername) {
        Task updatedTask = userService.assignTask(taskId, assigneeUsername);
        return ResponseEntity.ok(updatedTask);
    }

}
