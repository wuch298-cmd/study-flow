package com.wuch298.studyflow.service;

import com.wuch298.studyflow.entity.task.Task;
import com.wuch298.studyflow.entity.task.TaskStatus;

import java.util.List;

public interface TaskService {
    List<Task> findTasks(TaskStatus status);
    Task save(Task task);
    Task findById(Long id);
    void deleteById(Long id);
}
