package com.wuch298.studyflow.service;

import com.wuch298.studyflow.entity.task.Task;
import com.wuch298.studyflow.entity.task.TaskStatus;
import com.wuch298.studyflow.exception.TaskNotFoundException;
import com.wuch298.studyflow.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> findTasks(TaskStatus status){
        if(status == null){
            return taskRepository.findAll();
        }
        else
            return taskRepository.findByStatus(status);
    }
    @Override
    public Task save(Task task){
        return taskRepository.save(task);
    }
    @Override
    public Task findById(Long id){
        return taskRepository.findById(id).orElseThrow(()->new TaskNotFoundException(id));
    }
    @Override
    public void deleteById(Long id){
        taskRepository.deleteById(id);
    }
}
