package ru.rtischev.task_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private Long taskId;
    private Long userId;
    private String content;
    private Date createdAt;

}

