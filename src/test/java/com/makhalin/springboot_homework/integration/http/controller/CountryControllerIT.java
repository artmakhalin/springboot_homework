package com.makhalin.springboot_homework.integration.http.controller;

import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.mapper.CountryMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.makhalin.springboot_homework.dto.CountryCreateEditDto.Fields.name;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@AutoConfigureMockMvc
class CountryControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CountryMapper countryMapper;

    @Test
    @SneakyThrows
    void findAll() {
        mockMvc.perform(get("/countries"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("country/countries"),
                       model().attributeExists("countries"),
                       model().attribute("countries", hasSize(4))
               );
    }

    @Test
    @SneakyThrows
    void findById() {
        mockMvc.perform(get("/countries/" + usa.getId()))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("country/country"),
                       model().attributeExists("country"),
                       model().attribute("country", equalTo(countryMapper.mapRead(usa))),
                       model().attribute("cities", hasSize(2))
               );
    }

    @Test
    @SneakyThrows
    void createGet() {
        mockMvc.perform(get("/countries/create"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("country/create"),
                       model().attributeExists("country")
               );
    }

    @Test
    @SneakyThrows
    void create() {
        mockMvc.perform(post("/countries")
                       .param(name, "Portugal"))
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrlPattern("/countries/{\\d+}")
               );
    }

    @Test
    @SneakyThrows
    void update() {
        mockMvc.perform(post("/countries/" + usa.getId() + "/update")
                       .param(name, "Australia"))
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrl("/countries/" + usa.getId())
               );

    }

    @Test
    @SneakyThrows
    void delete() {
        mockMvc.perform(post("/countries/" + uk.getId() + "/delete"))
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrl("/countries")
               );
    }
}