package com.wuch298.studyflow.controller;

import com.wuch298.studyflow.entity.task.Task;
import com.wuch298.studyflow.entity.task.TaskPriority;
import com.wuch298.studyflow.entity.task.TaskStatus;
import com.wuch298.studyflow.repository.TaskRepository;
import com.wuch298.studyflow.service.TaskService;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
class TaskControllerTests {
    @Mock
    private TaskService taskService;
    @Mock
    private Model model;
    @Test
    void listTasksWithoutStatusShouldFindAllTasks()
    {
        TaskController taskController = new TaskController(taskService);
        taskController.listTasks(null,model);
        verify(taskService).findTasks(null);
    }
    @Test
    void listTasksWithStatusShouldFindByStatus()
    {
        TaskController taskController = new TaskController(taskService);
        taskController.listTasks(TaskStatus.TODO,model);
        verify(taskService).findTasks(TaskStatus.TODO);
    }
    @Test
    void listTasksShouldAddDataToModelAndReturnListView()
    {
        TaskController taskController = new TaskController(taskService);
        List<Task> tasks = List.of(new Task());//只是一个新对象
        when(taskService.findTasks(null)).thenReturn(tasks);//这里确定了tasks是查数据库返回的
        String viewName = taskController.listTasks(null,model);

        verify(model).addAttribute("tasks",tasks);
        verify(model).addAttribute("selectedStatus", null);
        verify(model).addAttribute("statuses", TaskStatus.values());

        assertThat(viewName).isEqualTo("tasks/list.html");
    }
    @Test
    void showCreatFormShouldAddDataToModelAndReturnFormView()
    {
        TaskController taskController = new TaskController(taskService);//为了调用showCreatForm验证返回名
        String viewName = taskController.showCreatForm(model);
        verify(model).addAttribute(eq("task"),any(Task.class));
        verify(model).addAttribute("priorities", TaskPriority.values());

        assertThat(viewName).isEqualTo("tasks/form.html");
    }
    @Test
    void createTaskShouldSaveTaskAndReturnView()
    {
        TaskController taskController = new TaskController(taskService);
        Task task = new Task();
        String viewName = taskController.createTask(task);
        verify(taskService).save(task);
        assertThat(viewName).isEqualTo("redirect:/tasks");
    }
}

