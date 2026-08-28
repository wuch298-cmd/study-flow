package com.wuch298.studyflow.controller;

import com.wuch298.studyflow.entity.task.Task;
import com.wuch298.studyflow.entity.task.TaskPriority;
import com.wuch298.studyflow.entity.task.TaskStatus;
import com.wuch298.studyflow.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
