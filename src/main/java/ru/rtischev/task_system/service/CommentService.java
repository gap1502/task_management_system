package ru.rtischev.task_system.service;

import ru.rtischev.task_system.model.Comment;

import java.util.Set;


public interface CommentService {


    Comment addCommentToTask(Long taskId, String content);

    Set<Comment> getAllCommentsForTask(Long taskId);

    void deleteComment(Long commentId);
}
