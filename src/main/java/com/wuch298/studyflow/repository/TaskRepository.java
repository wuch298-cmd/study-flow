package com.wuch298.studyflow.repository;

import com.wuch298.studyflow.entity.task.Task;
import com.wuch298.studyflow.entity.task.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long>//继承JPA方法,管理task，Id为long
{
    List<Task> findByStatus(TaskStatus status);
}

