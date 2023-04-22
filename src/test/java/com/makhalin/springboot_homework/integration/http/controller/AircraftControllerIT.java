package com.makhalin.springboot_homework.integration.http.controller;

import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.mapper.AircraftMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.makhalin.springboot_homework.dto.AircraftCreateEditDto.Fields.model;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@AutoConfigureMockMvc
class AircraftControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AircraftMapper aircraftMapper;

    @Test
    @SneakyThrows
    void findAll() {
        mockMvc.perform(get("/aircraft"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("aircraft/aircraftList"),
                       model().attributeExists("aircraftList"),
                       model().attribute("aircraftList", hasSize(4))
               );
    }

    @Test
    @SneakyThrows
    void findById() {
        mockMvc.perform(get("/aircraft/" + airbus320.getId()))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("aircraft/aircraft"),
                       model().attributeExists("aircraft"),
                       model().attribute("aircraft", equalTo(aircraftMapper.mapRead(airbus320)))
               );
    }

    @Test
    @SneakyThrows
    void createGet() {
        mockMvc.perform(get("/aircraft/create"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("aircraft/create"),
                       model().attributeExists("aircraft")
               );
    }

    @Test
    @SneakyThrows
    void create() {
        mockMvc.perform(post("/aircraft")
                       .with(csrf())
                       .param(model, "RRJ-95"))
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrlPattern("/aircraft/{\\d+}")
               );
    }

    @Test
    @SneakyThrows
    void update() {
        mockMvc.perform(post("/aircraft/" + airbus320.getId() + "/update")
                       .with(csrf())
                       .param(model, "XXX"))
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrl("/aircraft/" + airbus320.getId())
               );
    }

    @Test
    @SneakyThrows
    void delete() {
        mockMvc.perform(post("/aircraft/" + airbus330.getId() + "/delete")
                       .with(csrf()))
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrl("/aircraft")
               );
    }
}
