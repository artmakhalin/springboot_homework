package com.makhalin.springboot_homework.integration.http.controller;

import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.makhalin.springboot_homework.dto.CrewCreateEditDto.Fields.*;
import static com.makhalin.springboot_homework.dto.CrewCreateEditDto.Fields.mkkDate;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class RegistrationControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void registration() {
        mockMvc.perform(get("/registration"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("crew/registration"),
                       model().attributeExists("crew")
               );
    }

    @Test
    @SneakyThrows
    void create() {
        mockMvc.perform(post("/registration")
                       .with(csrf())
                       .param(email, "test@test.com")
                       .param(rawPassword, "test")
                       .param(firstname, "Test")
                       .param(lastname, "Test")
                       .param(birthDate, "1980-01-01")
                       .param(employmentDate, "2020-01-01")
                       .param(mkkDate, "2023-10-10"))
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrl("/login")
               );
    }
}
