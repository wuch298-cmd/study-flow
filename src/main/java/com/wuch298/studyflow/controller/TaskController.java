package com.wuch298.studyflow.controller;

import com.wuch298.studyflow.entity.task.Task;
import com.wuch298.studyflow.entity.task.TaskPriority;
import com.wuch298.studyflow.entity.task.TaskStatus;
import com.wuch298.studyflow.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskController {
    private final TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")//显示任务列表
    public String listTasks(@RequestParam(required = false) TaskStatus status, Model model) {
        List<Task> tasks = taskService.findTasks(status);
        model.addAttribute("tasks", tasks);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("statuses", TaskStatus.values());

        return "tasks/list";//任务列表
    }
    @GetMapping("/tasks/new")//打开新增任务页面
    public String showCreatForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("priorities", TaskPriority.values());

        return "tasks/form";//表单
    }
    @PostMapping("/tasks")//点击保存表单
    public String createTask(@Valid @ModelAttribute Task task, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("priorities", TaskPriority.values());
            return "tasks/form";//表单
        }
        taskService.save(task);

        return "redirect:/tasks";//刷新回到任务列表
    }
    @GetMapping("/tasks/{id}/edit")//打开对应id的编辑页面。
    public String showEditForm(@PathVariable long id, Model model)//URL路径所以是PathVariable
    {
        Task task = taskService.findById(id);
        model.addAttribute("task", task);
        model.addAttribute("priorities", TaskPriority.values());
        model.addAttribute("statuses", TaskStatus.values());

        return "tasks/form";//回显表单
    }
    @PostMapping("/tasks/{id}")//用户点击保存修改
    public String updateTask(@PathVariable long id, @Valid@ModelAttribute Task task, BindingResult bindingResult, Model model) {


        if (bindingResult.hasErrors()) {
            model.addAttribute("priorities", TaskPriority.values());
            model.addAttribute("statuses", TaskStatus.values());

            return "tasks/form";
        }
        Task existingTask = taskService.findById(id);
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setPriority(task.getPriority());
        existingTask.setDeadline(task.getDeadline());
        existingTask.setStatus(task.getStatus());

        taskService.save(existingTask);

        return "redirect:/tasks";//回到任务列表
    }
    @PostMapping("/tasks/{id}/delete")//用户点击删除
    public String deleteTask(@PathVariable long id) {
        taskService.deleteById(id);
        return "redirect:/tasks";//刷新回到列表
    }
}
