package com.wuch298.studyflow.repository;

import com.wuch298.studyflow.entity.task.Task;
import com.wuch298.studyflow.entity.task.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@DataJpaTest
public class TaskRepositoryTests {
    @Autowired
    private TaskRepository taskRepository;
    @Test
    void findByStatusShouldReturnOnlyMatchingTasks()
    {
        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setStatus(TaskStatus.TODO);

        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setStatus(TaskStatus.DONE);

        Task task3 = new Task();
        task3.setTitle("Task 3");
        task3.setStatus(TaskStatus.TODO);

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);

        List<Task> tasks = taskRepository.findByStatus(TaskStatus.TODO);
        assertThat(tasks).hasSize(2);
        assertThat(tasks).extracting(Task::getTitle).containsExactly("Task 1", "Task 3");
    }
}
