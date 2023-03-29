package com.makhalin.springboot_homework;

import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class TestApplicationRunner {

    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13");

    static {
        postgres.start();

        System.setProperty("POSTGRES_URL", postgres.getJdbcUrl());
        System.setProperty("POSTGRES_USER", postgres.getUsername());
        System.setProperty("POSTGRES_PASSWORD", postgres.getPassword());
    }
}
