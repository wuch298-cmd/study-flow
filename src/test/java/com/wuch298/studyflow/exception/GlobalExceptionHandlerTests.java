package com.wuch298.studyflow.exception;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GlobalExceptionHandlerTests {
    @Test
    void handleTaskNotFoundShouldAddMessageAndReturn404View(){
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Model model= mock(Model.class);
        TaskNotFoundException exception = new TaskNotFoundException(1L);
        String viewName=handler.handleTaskNotFound(exception, model);
        verify(model).addAttribute("message", "Task not found: 1");
        assertThat(viewName).isEqualTo("error/404");
    }
}
