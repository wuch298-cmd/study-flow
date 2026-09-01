package com.wuch298.studyflow.controller;

import com.wuch298.studyflow.entity.task.Task;
import com.wuch298.studyflow.entity.task.TaskPriority;
import com.wuch298.studyflow.entity.task.TaskStatus;
import com.wuch298.studyflow.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskController {
    private final TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String listTasks(@RequestParam(required = false) TaskStatus status, Model model) {
        List<Task> tasks = taskService.findTasks(status);
        model.addAttribute("tasks", tasks);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("statuses", TaskStatus.values());

        return "tasks/list.html";
    }
    @GetMapping("/tasks/new")
    public String showCreatForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("priorities", TaskPriority.values());

        return "tasks/form.html";
    }
    @PostMapping("tasks")
    public String createTask(@ModelAttribute Task task) {
        taskService.save(task);

        return "redirect:/tasks";
    }
    @GetMapping("/tasks/{id}/edit")
    public String showEditForm(@PathVariable long id, Model model)//URL路径所以是PathVariable
    {
        Task task = taskService.findById(id);
        model.addAttribute("task", task);
        model.addAttribute("priorities", TaskPriority.values());
        model.addAttribute("statuses", TaskStatus.values());

        return "tasks/form.html";
    }
    @PostMapping("/tasks/{id}")
    public String updateTask(@PathVariable long id, @ModelAttribute Task task) {
        Task existingTask = taskService.findById(id);

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setPriority(task.getPriority());
        existingTask.setDeadline(task.getDeadline());
        existingTask.setStatus(task.getStatus());

        taskService.save(existingTask);

        return "redirect:/tasks";
    }
    @PostMapping("/tasks/{id}/delete")
    public String deleteTask(@PathVariable long id) {
        taskService.deleteById(id);
        return "redirect:/tasks";
    }
}
