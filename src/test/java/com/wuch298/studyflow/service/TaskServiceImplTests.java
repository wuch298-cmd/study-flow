package com.wuch298.studyflow.service;

import com.wuch298.studyflow.entity.task.Task;
import com.wuch298.studyflow.entity.task.TaskStatus;
import com.wuch298.studyflow.exception.TaskNotFoundException;
import com.wuch298.studyflow.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    @Test
    void findByIdShouldReturnTask() {
        TaskService taskService = new TaskServiceImpl(taskRepository);
        Task task = new Task();
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Task result = taskService.findById(1L);
        verify(taskRepository).findById(1L);
        assertThat(result).isSameAs(task);
    }
    @Test
    void findByIdShouldThrowTaskNotFoundExceptionWhenTaskNotFound() {
        TaskService taskService =
                new TaskServiceImpl(taskRepository);

        when(taskRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.findById(1L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessage("Task not found: 1");

        verify(taskRepository).findById(1L);
    }
    @Test
    void deleteByIdShouldCallRepositoryDelete() {
        TaskService taskService = new TaskServiceImpl(taskRepository);
        taskService.deleteById(1L);
        verify(taskRepository).deleteById(1L);
    }
}
