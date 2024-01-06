package ru.rtischev.task_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rtischev.task_system.enums.TaskPriority;
import ru.rtischev.task_system.enums.TaskStatus;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private Long authorId;
    private Long assigneeId;
    private Set<CommentDto> comments = new HashSet<>();

}
