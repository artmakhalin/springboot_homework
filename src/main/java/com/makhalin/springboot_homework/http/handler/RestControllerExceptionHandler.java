package com.makhalin.springboot_homework.http.handler;

import com.makhalin.springboot_homework.dto.ErrorResponse;
import com.makhalin.springboot_homework.exception.BadRequestException;
import com.makhalin.springboot_homework.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public ErrorResponse constraintViolationException(ConstraintViolationException e) {
        log.error("Failed to return response", e);

        return ErrorResponse.of(e.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        var message = ex.getBindingResult()
                        .getFieldErrors()
                        .get(0)
                        .getDefaultMessage();
        log.error("Failed to return response", ex);

        return ResponseEntity.badRequest()
                             .body(ErrorResponse.of(message));
    }
}
