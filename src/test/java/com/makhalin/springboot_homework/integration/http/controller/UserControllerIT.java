package com.makhalin.springboot_homework.integration.http.controller;

import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static com.makhalin.springboot_homework.dto.FlightFilter.Fields.month;
import static com.makhalin.springboot_homework.dto.FlightFilter.Fields.year;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
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
                       view().name("user/monthly"),
                       model().attributeExists("flights"),
                       model().attribute("flights", hasSize(2)),
                       model().attributeExists("flightTime"),
                       model().attribute("flightTime", hasProperty("hours", equalTo(10))),
                       model().attribute("flightTime", hasProperty("minutes", equalTo(45)))
               );
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "alex@test.com", password = "test", authorities = {"USER"})
    void currentMonthlyFlights() {
        mockMvc.perform(get("/monthly"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("user/monthly"),
                       model().attributeExists("month"),
                       model().attribute("month", equalTo(LocalDate.now()
                                                                   .getMonthValue())),
                       model().attributeExists("year"),
                       model().attribute("year", equalTo(LocalDate.now()
                                                                  .getYear()))
               );
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "alex@test.com", password = "test", authorities = {"USER"})
    void yearStatistics() {
        mockMvc.perform(get("/statistics")
                       .param(year, "2023"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("user/statistics"),
                       model().attributeExists("statisticsDto"),
                       model().attribute("statisticsDto", hasProperty("statistics")),
                       model().attribute("statisticsDto", hasProperty("totalTime", hasProperty("hours", equalTo(17)))),
                       model().attribute("statisticsDto", hasProperty("totalTime", hasProperty("minutes", equalTo(39))))
               );
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "alex@test.com", password = "test", authorities = {"USER"})
    void currentYearStatistics() {
        mockMvc.perform(get("/statistics"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       view().name("user/statistics"),
                       model().attributeExists("year"),
                       model().attribute("year", equalTo(LocalDate.now()
                                                                  .getYear()))
               );
    }
}
