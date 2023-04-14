package com.makhalin.springboot_homework.http.handler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackages = "com.makhalin.springboot_homework.http.rest")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
}
