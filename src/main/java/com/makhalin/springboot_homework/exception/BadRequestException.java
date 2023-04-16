package com.makhalin.springboot_homework.exception;

import java.util.function.Supplier;

public class BadRequestException extends RuntimeException {

    public BadRequestException (String message) {
        super(message);
    }

    public static Supplier<BadRequestException> badRequest(String message) {
        return () -> new BadRequestException(message);
    }
}
