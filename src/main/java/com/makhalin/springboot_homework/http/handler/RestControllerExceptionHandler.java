package com.makhalin.springboot_homework.http.handler;

import com.makhalin.springboot_homework.dto.ErrorResponse;
import com.makhalin.springboot_homework.exception.BadRequestException;
import com.makhalin.springboot_homework.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(basePackages = "com.makhalin.springboot_homework.http.rest")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public ErrorResponse notFoundException(NotFoundException e) {
        log.error("Failed to return response", e);

        return ErrorResponse.of(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class})
    public ErrorResponse badRequestException(BadRequestException e) {
        log.error("Failed to return response", e);

        return ErrorResponse.of(e.getMessage());
    }
}
