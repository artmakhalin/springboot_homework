package com.makhalin.springboot_homework.exception;

import java.util.function.Supplier;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException notFoundException(String message) {
        return new NotFoundException(message);
    }

    public static Supplier<NotFoundException> notFound(String message) {
        return () -> new NotFoundException(message);
    }
}
