package ru.rtischev.task_system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.rtischev.task_system.exceptions.CommentNotFoundException;
import ru.rtischev.task_system.exceptions.TaskNotFoundException;
import ru.rtischev.task_system.exceptions.UnauthorizedUserException;
import ru.rtischev.task_system.model.Comment;
import ru.rtischev.task_system.model.Task;
import ru.rtischev.task_system.model.User;
import ru.rtischev.task_system.repository.CommentRepository;
import ru.rtischev.task_system.repository.TaskRepository;
import ru.rtischev.task_system.service.CommentService;
import ru.rtischev.task_system.service.CurrentUserDetailsService;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CommentServiceImpl implements CommentService {

    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    private final CurrentUserDetailsService currentUserDetailsService;

    @Autowired
    public CommentServiceImpl(TaskRepository taskRepository, CommentRepository commentRepository, CurrentUserDetailsService currentUserDetailsService) {
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
        this.currentUserDetailsService = currentUserDetailsService;
    }

    @Override
    public Comment addCommentToTask(Long taskId, String content) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        User currentUser = currentUserDetailsService.getCurrentUser();
        if (currentUser != null) {
            Comment comment = new Comment();
            comment.setTask(task);
            comment.setUser(currentUser);
            comment.setContent(content);
            comment.setCreatedAt(new Date());

            return commentRepository.save(comment);
        } else {
            throw new UnauthorizedUserException("Current user not found");
        }


    }

    @Override
    public Set<Comment> getAllCommentsForTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        List<Comment> comments = commentRepository.findByTaskId(taskId);

        if (comments.isEmpty()) {
            throw new CommentNotFoundException("No comments found for task with id: " + taskId);
        }

        task.setComments(new HashSet<>(comments));
        return task.getComments();
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        if (commentRepository.findById(commentId).isEmpty()) {
            throw new CommentNotFoundException("Comment not found with ID: " + commentId);
        }

        commentRepository.deleteById(commentId);
    }
}
