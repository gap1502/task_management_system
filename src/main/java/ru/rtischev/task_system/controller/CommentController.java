package ru.rtischev.task_system.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rtischev.task_system.dto.CommentDto;
import ru.rtischev.task_system.model.Comment;
import ru.rtischev.task_system.service.CommentService;

import java.util.Set;


@RestController
@RequestMapping("/api/comments")
@Tag(name = "Комментарии", description = "взимодествие с комментариями")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(
            summary = "Создание комментария к задаче",
            description = "Позволяет пользователю создать комментарий"
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping("/tasks/{taskId}")
    public ResponseEntity<Comment> addCommentToTask(
            @PathVariable @Parameter(description = "Идентификатор задачи") Long taskId,
            @RequestBody CommentDto commentDto) {
        Comment comment = commentService.addCommentToTask(taskId, commentDto.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @Operation(
            summary = "Получение всех комментарий задачи"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<Set<Comment>> getAllCommentsForTask(@PathVariable @Parameter(description = "Идентификатор задачи") Long taskId) {
        Set<Comment> comments = commentService.getAllCommentsForTask(taskId);
        return ResponseEntity.ok(comments);
    }

    @Operation(
            summary = "Удаление комментария задачи"
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable @Parameter(description = "Идентификатор комментария") Long commentId) {

        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();

    }
}
