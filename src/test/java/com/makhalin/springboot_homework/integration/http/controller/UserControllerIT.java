package com.makhalin.springboot_homework.integration.http.controller;

import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static com.makhalin.springboot_homework.dto.FlightFilter.Fields.month;
import static com.makhalin.springboot_homework.dto.FlightFilter.Fields.year;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class UserControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    @WithMockUser(username = "alex@test.com", password = "test", authorities = {"USER"})
    void monthlyFlights() {
        mockMvc.perform(get("/monthly")
                       .param(month, "3")
                       .param(year, "2023"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("crew/monthly"),
                       model().attributeExists("flights"),
                       model().attribute("flights", hasSize(2)),
                       model().attributeExists("hours"),
                       model().attribute("hours", equalTo(10)),
                       model().attributeExists("minutes"),
                       model().attribute("minutes", equalTo(45))
               );
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "alex@test.com", password = "test", authorities = {"USER"})
    void currentMonthlyFlights() {
        mockMvc.perform(get("/monthly"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("crew/monthly"),
                       model().attributeExists("month"),
                       model().attribute("month", equalTo(LocalDate.now().getMonthValue())),
                       model().attributeExists("year"),
                       model().attribute("year", equalTo(LocalDate.now().getYear()))
               );
    }
}
