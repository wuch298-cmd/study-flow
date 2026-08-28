package com.wuch298.studyflow.service;

import com.wuch298.studyflow.entity.task.Task;
import com.wuch298.studyflow.entity.task.TaskStatus;
import com.wuch298.studyflow.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTests {
    @Mock
    private TaskRepository taskRepository;
    @Test
    void findTasksWithNullStatusShouldFindAll() {
        TaskService taskService = new TaskServiceImpl(taskRepository);
        taskService.findTasks(null);
        verify(taskRepository).findAll();
    }
    @Test
    void findTasksWithStatusShouldFindByStatus() {
        TaskService taskService = new TaskServiceImpl(taskRepository);
        taskService.findTasks(TaskStatus.TODO);
        verify(taskRepository).findByStatus(TaskStatus.TODO);
    }
    @Test
    void saveShouldCallRepositorySave() {
        TaskService taskService = new TaskServiceImpl(taskRepository);

        Task task = new Task();

        taskService.save(task);

        verify(taskRepository).save(task);
    }
}
