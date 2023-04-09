package com.makhalin.springboot_homework.integration.http.controller;

import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import com.makhalin.springboot_homework.mapper.CityReadMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.makhalin.springboot_homework.dto.CityCreateEditDto.Fields.countryId;
import static com.makhalin.springboot_homework.dto.CityCreateEditDto.Fields.name;
import static com.makhalin.springboot_homework.dto.CityFilter.Fields.country;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@AutoConfigureMockMvc
class CityControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CityReadMapper cityReadMapper;

    @Test
    @SneakyThrows
    void findAll() {
        mockMvc.perform(get("/cities")
                       .param(country, "usa"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("city/cities"),
                       model().attributeExists("cities"),
                       model().attribute("cities", hasProperty("content", hasSize(2)))
               );
    }

    @Test
    @SneakyThrows
    void findById() {
        mockMvc.perform(get("/cities/" + newYork.getId()))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("city/city"),
                       model().attributeExists("city"),
                       model().attribute("city", equalTo(cityReadMapper.map(newYork)))
               );
    }

    @Test
    @SneakyThrows
    void createGet() {
        mockMvc.perform(get("/cities/create"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("city/create"),
                       model().attributeExists("city")
               );
    }

    @Test
    @SneakyThrows
    void create() {
        mockMvc.perform(post("/cities")
                       .param(name, "Miami")
                       .param(countryId, usa.getId()
                                            .toString()))
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrlPattern("/cities/{\\d+}")
               );
    }

    @Test
    @SneakyThrows
    void update() {
        mockMvc.perform(post("/cities/" + newYork.getId() + "/update")
                       .param(name, "Miami")
                       .param(countryId, usa.getId().toString()))
               .andExpectAll(
                       status().is3xxRedirection(),
                       redirectedUrl("/cities/" + newYork.getId())
               );
    }

    @Test
    @SneakyThrows
    void delete() {
        mockMvc.perform(post("/cities/" + lion.getId() + "/delete"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/cities")
                );
    }
}