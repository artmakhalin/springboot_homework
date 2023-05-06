package com.makhalin.springboot_homework.integration.http.controller;

import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.mapper.FlightCrewMapper;
import com.makhalin.springboot_homework.mapper.FlightMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.makhalin.springboot_homework.dto.FlightCrewCreateEditDto.Fields.*;
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
class FlightCrewControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FlightCrewMapper flightCrewMapper;

    @Autowired
    private FlightMapper flightMapper;

    @Test
    @SneakyThrows
    void findById() {
        mockMvc.perform(get("/flightCrew/" + ledVogAlex.getId()))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("flightCrew/flightCrew"),
                       model().attributeExists("flightCrew"),
                       model().attribute("flightCrew", equalTo(flightCrewMapper.mapRead(ledVogAlex))),
                       model().attributeExists("crewList"),
                       model().attribute("crewList", hasSize(4)),
                       model().attributeExists("classesOfService")
               );
    }

    @Test
    @SneakyThrows
    void createGet() {
        mockMvc.perform(get("/flightCrew/" + svoJfk.getId() + "/create"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("flightCrew/create"),
                       model().attributeExists("flight"),
                       model().attribute("flight", equalTo(flightMapper.mapRead(svoJfk))),
                       model().attributeExists("crewList"),
                       model().attribute("crewList", hasSize(4)),
                       model().attributeExists("classesOfService")
               );
    }

    @Test
    @SneakyThrows
    void create() {
        mockMvc.perform(post("/flightCrew")
                       .with(csrf())
                       .param(flightId, svoJfk.getId().toString())
                       .param(crewId, bob.getId().toString())
                       .param(classOfService, "ECONOMY")
                       .param(isPassenger, "true")
                       .param(isTurnaround, "true")
               )
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrlPattern("/flightCrew/{\\d+}")
               );
    }

    @Test
    @SneakyThrows
    void update() {
        mockMvc.perform(post("/flightCrew/" + ledVogAlex.getId() + "/update")
                       .with(csrf())
                       .param(flightId, ledVog.getId().toString())
                       .param(crewId, bob.getId().toString())
                       .param(classOfService, "ECONOMY")
                       .param(isPassenger, "true")
                       .param(isTurnaround, "true")
               )
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrl("/flights/" + ledVog.getId())
               );
    }

    @Test
    @SneakyThrows
    void delete() {
        mockMvc.perform(post("/flightCrew/" + ledVogAlex.getId() + "/delete")
                       .with(csrf()))
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrl("/flights")
               );
    }
}
