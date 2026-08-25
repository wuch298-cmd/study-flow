package com.wuch298.studyflow.entity.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
//实体表格
@Entity//对应数据库，说明是数据库实体
@Table(name = "tasks")//对应MYSQL表名
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增自己生成
    private Long id;//任务id，主键

    @NotBlank//应用层要求非空
    @Size(max = 100)//应用层要求大小最大100
    @Column(nullable = false, length = 100)//数据库层要求
    private String title;//任务标题

    @Size(max = 2000)
    @Column(length = 2000)//数据库规则
    private String description;//任务描述

    @Enumerated(EnumType.STRING)//枚举存成字符串
    @Column(nullable = false, length = 20)
    private TaskStatus status = TaskStatus.TODO;//任务进程

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TaskPriority priority = TaskPriority.MEDIUM;//任务优先级

    private LocalDate deadline;//任务期限

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;//创建时间

    @Column(nullable = false)
    private LocalDateTime updatedAt;//更新时间

    @PrePersist//第一次保存前执行
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate//更新前执行
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
