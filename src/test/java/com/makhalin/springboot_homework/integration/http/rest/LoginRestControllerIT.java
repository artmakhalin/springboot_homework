package com.makhalin.springboot_homework.integration.http.rest;

import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class LoginRestControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    @WithAnonymousUser
    void login() {
        mockMvc.perform(post("/api/v1/login")
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(
                               """
                                       {
                                           "username": "alex@test.com",
                                           "password": "test"
                                       }
                                       """
                       )
               )
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.email").value("alex@test.com"),
                       jsonPath("$.firstname").value("Alex"),
                       jsonPath("$.lastname").value("Test"),
                       jsonPath("$.birthDate").value("1995-01-01"),
                       jsonPath("$.employmentDate").value("2015-12-05"),
                       jsonPath("$.role").value("USER")
               );
    }
}
