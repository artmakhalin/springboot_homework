package com.makhalin.springboot_homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringbootHomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootHomeworkApplication.class, args);
    }

}
