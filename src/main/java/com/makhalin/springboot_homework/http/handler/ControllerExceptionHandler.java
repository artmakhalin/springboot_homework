package com.makhalin.springboot_homework.http.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public String handleExceptions(Exception exception, Model model) {
        log.error("Failed to return response", exception);
        model.addAttribute("errors", exception.getMessage());

        return "error/error500";
    }
}
