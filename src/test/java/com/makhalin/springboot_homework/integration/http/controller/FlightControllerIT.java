package com.makhalin.springboot_homework.integration.http.controller;

import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.mapper.FlightMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.makhalin.springboot_homework.dto.FlightCreateEditDto.Fields.*;
import static com.makhalin.springboot_homework.dto.FlightFilter.Fields.departureAirport;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
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
class FlightControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FlightMapper flightMapper;

    @Test
    @SneakyThrows
    void findAll() {
        mockMvc.perform(get("/flights")
                       .param(departureAirport, "svo"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("flight/flights"),
                       model().attributeExists("flights"),
                       model().attribute("flights", hasProperty("content", hasSize(4)))
               );
    }

    @Test
    @SneakyThrows
    void findById() {
        mockMvc.perform(get("/flights/" + svoJfk.getId()))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("flight/flight"),
                       model().attributeExists("flight"),
                       model().attribute("flight", equalTo(flightMapper.mapRead(svoJfk)))
               );
    }

    @Test
    @SneakyThrows
    void createGet() {
        mockMvc.perform(get("/flights/create"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("flight/create"),
                       model().attributeExists("flight")
               );
    }

    @Test
    @SneakyThrows
    void create() {
        mockMvc.perform(post("/flights")
                       .with(csrf())
                       .param(flightNo, "SU555")
                       .param(departureAirportId, jfk.getId().toString())
                       .param(arrivalAirportId, sea.getId().toString())
                       .param(departureDate, "2022-02-27")
                       .param(hours, "5")
                       .param(minutes, "35")
                       .param(aircraftId, airbus320.getId()
                                             .toString()))
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrlPattern("/flights/{\\d+}")
               );
    }

    @Test
    @SneakyThrows
    void update() {
        mockMvc.perform(post("/flights/" + svoJfk.getId() + "/update")
                       .with(csrf())
                       .param(flightNo, "SU555")
                       .param(departureAirportId, jfk.getId().toString())
                       .param(arrivalAirportId, sea.getId().toString())
                       .param(departureDate, "2022-02-27")
                       .param(hours, "5")
                       .param(minutes, "35")
                       .param(aircraftId, airbus320.getId()
                                                   .toString()))
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrl("/flights/" + svoJfk.getId())
               );
    }

    @Test
    @SneakyThrows
    void delete() {
        mockMvc.perform(post("/flights/" + jfkCdg.getId() + "/delete")
                       .with(csrf()))
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrl("/flights")
               );
    }
}
