package com.makhalin.springboot_homework.integration.http.controller;

import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@AutoConfigureMockMvc
class LoginControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void loginPage() {
        mockMvc.perform(get("/login"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("crew/login")
               );
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "alex@test.com", password = "test", authorities = {"USER"})
    void mainPage() {
        mockMvc.perform(get("/main"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("crew/main"),
                       model().attributeExists("crew"),
                       model().attribute("crew", hasProperty("email", equalTo("alex@test.com"))),
                       model().attributeExists("crewAircraftList"),
                       model().attribute("crewAircraftList", hasSize(2))
               );
    }
}
