package com.makhalin.springboot_homework.integration.http.rest;

import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class RegistrationRestControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void create() {
        mockMvc.perform(post("/api/v1/registration")
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(
                               """
                                       {
                                           "email": "test@test.com",
                                           "rawPassword": "test",
                                           "firstname": "Test",
                                           "lastname": "Test",
                                           "birthDate": "1990-04-21",
                                           "employmentDate": "2020-04-21"
                                       }
                                       """
                       )
               )
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.email").value("test@test.com"),
                       jsonPath("$.firstname").value("Test"),
                       jsonPath("$.lastname").value("Test"),
                       jsonPath("$.birthDate").value("1990-04-21"),
                       jsonPath("$.employmentDate").value("2020-04-21"),
                       jsonPath("$.role").value("USER")
               );
    }
}
