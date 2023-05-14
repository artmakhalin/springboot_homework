package com.makhalin.springboot_homework.integration.http.controller;

import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.mapper.CrewAircraftMapper;
import com.makhalin.springboot_homework.mapper.CrewMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.makhalin.springboot_homework.dto.CrewAircraftCreateEditDto.Fields.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@AutoConfigureMockMvc
public class CrewAircraftControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CrewAircraftMapper crewAircraftMapper;

    @Autowired
    private CrewMapper crewMapper;

    @Test
    @SneakyThrows
    void findById() {
        mockMvc.perform(get("/crewAircraft/" + alexAirbus320.getId()))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("crewAircraft/crewAircraft"),
                       model().attributeExists("crewAircraft"),
                       model().attribute("crewAircraft", equalTo(crewAircraftMapper.mapRead(alexAirbus320))),
                       model().attributeExists("crewList"),
                       model().attribute("crewList", hasSize(4)),
                       model().attributeExists("aircraftList"),
                       model().attribute("aircraftList", hasSize(4))
               );
    }

    @Test
    @SneakyThrows
    void createGet() {
        mockMvc.perform(get("/crewAircraft/" + alex.getId() + "/create"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("crewAircraft/create"),
                       model().attributeExists("crew"),
                       model().attribute("crew", equalTo(crewMapper.mapRead(alex))),
                       model().attributeExists("crewAircraft"),
                       model().attributeExists("aircraftList"),
                       model().attribute("aircraftList", hasSize(4))
               );
    }

    @Test
    @SneakyThrows
    void create() {
        mockMvc.perform(post("/crewAircraft")
                       .with(csrf())
                       .param(aircraftId, airbus330.getId().toString())
                       .param(crewId, alex.getId().toString())
                       .param(permitDate, "2021-01-01")
               )
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrlPattern("/crewAircraft/{\\d+}")
               );
    }

    @Test
    @SneakyThrows
    void update() {
        mockMvc.perform(post("/crewAircraft/" + alexBoeing777.getId() + "/update")
                       .with(csrf())
                       .param(aircraftId, airbus330.getId().toString())
                       .param(crewId, alex.getId().toString())
                       .param(permitDate, "2021-01-01")
               )
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrl("/crew/" + alex.getId())
               );
    }

    @Test
    @SneakyThrows
    void delete() {
        mockMvc.perform(post("/crewAircraft/" + bobBoeing777.getId() + "/delete")
                       .with(csrf()))
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrl("/crew")
               );
    }
}
