package com.makhalin.springboot_homework.integration.http.controller;

import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.mapper.CrewMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.makhalin.springboot_homework.dto.CrewCreateEditDto.Fields.*;
import static com.makhalin.springboot_homework.dto.CrewFilter.Fields.email;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@AutoConfigureMockMvc
public class CrewControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CrewMapper crewMapper;

    @Test
    @SneakyThrows
    void findAllByFilter() {
        mockMvc.perform(get("/crew")
                       .param(email, "@test"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("crew/crewList"),
                       model().attributeExists("crewList"),
                       model().attribute("crewList", hasProperty("content", hasSize(4)))
               );
    }

    @Test
    @SneakyThrows
    void findById() {
        mockMvc.perform(get("/crew/" + alex.getId()))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("crew/crew"),
                       model().attributeExists("crew"),
                       model().attribute("crew", equalTo(crewMapper.mapRead(alex)))
               );
    }

    @Test
    @SneakyThrows
    void update() {
        mockMvc.perform(post("/crew/" + marta.getId() + "/update")
                       .with(csrf())
                       .param(email, "crew@email.com")
                       .param(firstname, "Crew")
                       .param(lastname, "Test")
                       .param(birthDate, "1990-01-01")
                       .param(employmentDate, "2020-01-01"))
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrl("/crew/" + marta.getId())
               );
    }

    @Test
    @SneakyThrows
    void delete() {
        mockMvc.perform(post("/crew/" + marta.getId() + "/delete")
                       .with(csrf()))
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrl("/crew")
               );
    }
}
