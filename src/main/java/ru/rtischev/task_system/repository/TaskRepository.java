package ru.rtischev.task_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.rtischev.task_system.model.Task;

import java.util.Optional;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @NonNull
    Optional<Task> findById(@NonNull Long taskId);

}
