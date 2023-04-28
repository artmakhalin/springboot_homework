package com.makhalin.springboot_homework.integration.http.controller;

import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.mapper.AirportMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.makhalin.springboot_homework.dto.AirportCreateEditDto.Fields.cityId;
import static com.makhalin.springboot_homework.dto.AirportCreateEditDto.Fields.code;
import static com.makhalin.springboot_homework.dto.AirportFilter.Fields.country;
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
class AirportControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AirportMapper airportMapper;

    @Test
    @SneakyThrows
    void findAll() {
        mockMvc.perform(get("/airports")
                       .param(country, "usa"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("airport/airports"),
                       model().attributeExists("airports"),
                       model().attribute("airports", hasProperty("content", hasSize(3)))
               );
    }

    @Test
    @SneakyThrows
    void findById() {
        mockMvc.perform(get("/airports/" + jfk.getId()))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("airport/airport"),
                       model().attributeExists("airport"),
                       model().attribute("airport", equalTo(airportMapper.mapRead(jfk)))
               );
    }

    @Test
    @SneakyThrows
    void createGet() {
        mockMvc.perform(get("/airports/create"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("airport/create"),
                       model().attributeExists("airport")
               );
    }

    @Test
    @SneakyThrows
    void create() {
        mockMvc.perform(post("/airports")
                       .with(csrf())
                       .param(code, "LGA")
                       .param(cityId, newYork.getId()
                                             .toString()))
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrlPattern("/airports/{\\d+}")
               );
    }

    @Test
    @SneakyThrows
    void update() {
        mockMvc.perform(post("/airports/" + jfk.getId() + "/update")
                       .with(csrf())
                       .param(code, "XXX")
                       .param(cityId, seattle.getId()
                                            .toString()))
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrl("/airports/" + jfk.getId())
               );
    }

    @Test
    @SneakyThrows
    void delete() {
        mockMvc.perform(post("/airports/" + ewr.getId() + "/delete")
                       .with(csrf()))
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrl("/airports")
               );
    }
}
