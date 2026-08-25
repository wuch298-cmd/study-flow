package com.wuch298.studyflow.entity.task;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskTests {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidator() {
        validatorFactory.close();
    }

    @Test
    void newTaskUsesTodoStatusAndMediumPriorityByDefault() {
        Task task = new Task();

        assertEquals(TaskStatus.TODO, task.getStatus());
        assertEquals(TaskPriority.MEDIUM, task.getPriority());
    }

    @Test
    void titleMustBePresentAndAtMostOneHundredCharacters() {
        Task blankTitleTask = new Task();
        blankTitleTask.setTitle("   ");

        Task longTitleTask = new Task();
        longTitleTask.setTitle("a".repeat(101));

        assertFalse(validator.validate(blankTitleTask).isEmpty());
        assertFalse(validator.validate(longTitleTask).isEmpty());
    }

    @Test
    void descriptionMustNotExceedTwoThousandCharacters() {
        Task task = new Task();
        task.setTitle("复习 Spring Boot");
        task.setDescription("a".repeat(2001));

        assertFalse(validator.validate(task).isEmpty());
    }

    @Test
    void lifecycleCallbacksMaintainCreationAndUpdateTimes() {
        Task task = new Task();

        task.onCreate();
        LocalDateTime createdAt = task.getCreatedAt();

        task.onUpdate();

        assertNotNull(createdAt);
        assertNotNull(task.getUpdatedAt());
        assertEquals(createdAt, task.getCreatedAt());
        assertTrue(!task.getUpdatedAt().isBefore(createdAt));
    }
}
