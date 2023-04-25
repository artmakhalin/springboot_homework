package com.makhalin.springboot_homework.http.rest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test@test.com", password = "test", authorities = {"ADMIN"})
class MessageRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void getMessageEn() {
        mockMvc.perform(get("/api/v1/admin/messages")
                       .param("key", "login.username")
                       .param("lang", "en"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       content().string("Username")
               );
    }

    @Test
    @SneakyThrows
    void getMessageRu() {
        mockMvc.perform(get("/api/v1/admin/messages")
                       .param("key", "login.password")
                       .param("lang", "ru"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       content().string("Пароль")
               );
    }
}