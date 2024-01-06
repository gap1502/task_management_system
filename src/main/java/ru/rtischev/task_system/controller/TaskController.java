package ru.rtischev.task_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rtischev.task_system.dto.TaskDto;
import ru.rtischev.task_system.model.Task;
import ru.rtischev.task_system.model.User;
import ru.rtischev.task_system.service.CurrentUserDetailsService;
import ru.rtischev.task_system.service.TaskService;

import java.util.Set;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Задачи", description = "взаимодействие с задачами")
public class TaskController {
    private final TaskService taskService;

    private final CurrentUserDetailsService currentUserDetailsService;

    @Autowired
    public TaskController(TaskService taskService, CurrentUserDetailsService currentUserDetailsService) {
        this.taskService = taskService;
        this.currentUserDetailsService = currentUserDetailsService;
    }

    @Operation(
            summary = "Получение всех задач"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping
    public ResponseEntity<Set<TaskDto>> getAllTasks() {
        Set<TaskDto> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @Operation(
            summary = "Получение задачи по идентификатору"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable @Parameter(description = "Идентификатор задачи") Long taskId) {
        TaskDto task = taskService.getTaskById(taskId);
        return ResponseEntity.ok(task);
    }

    @Operation(
            summary = "Удаление задачи по идентификатору"
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable @Parameter(description = "Идентификатор задачи") Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Обновление задачи по идентификатору"
    )
    @SecurityRequirement(name = "JWT")
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(
            @PathVariable @Parameter(description = "Идентификатор задачи") Long taskId,
            @RequestBody @Valid TaskDto taskDto) {
        TaskDto updatedTask = taskService.updateTask(taskId, taskDto);
        return ResponseEntity.ok(updatedTask);
    }

    @Operation(
            summary = "Создание новой задачи"
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDto taskDto) {
        User currentUser = currentUserDetailsService.getCurrentUser();
        if (currentUser != null) {
            Task task = taskService.createTask(taskDto, currentUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(task);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
