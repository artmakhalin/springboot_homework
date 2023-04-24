package com.makhalin.springboot_homework.integration.http.rest;

import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class CrewRestControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findAll() {
        mockMvc.perform(get("/api/v1/admin/crew"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.metadata.totalElements").value(4)
               );
    }

    @Test
    @SneakyThrows
    void findById() {
        mockMvc.perform(get("/api/v1/admin/crew/" + alex.getId()))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.id").value(alex.getId()),
                       jsonPath("$.email").value(alex.getEmail()),
                       jsonPath("$.firstname").value(alex.getPersonalInfo()
                                                         .getFirstname()),
                       jsonPath("$.lastname").value(alex.getPersonalInfo()
                                                        .getLastname())
               );
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundStatusIfNoCrewFound() {
        mockMvc.perform(get("/api/v1/admin/crew/" + 555))
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Crew not found with id 555")
               );
    }

    @Test
    @SneakyThrows
    void update() {
        mockMvc.perform(put("/api/v1/admin/crew/" + marta.getId())
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content("""
                               {
                                   "email": "test@test.com",
                                   "firstname": "Test",
                                   "lastname": "Test",
                                   "birthDate": "1990-04-21",
                                   "employmentDate": "2020-04-21",
                                   "mkkDate": "2025-04-21"
                               }
                               """
                       )
               )
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.email").value("test@test.com"),
                       jsonPath("$.firstname").value("Test"),
                       jsonPath("$.lastname").value("Test"),
                       jsonPath("$.birthDate").value("1990-04-21"),
                       jsonPath("$.employmentDate").value("2020-04-21"),
                       jsonPath("$.mkkDate").value("2025-04-21")
               );
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundStatusIfNoCrewFoundWhenUpdate() {
        mockMvc.perform(put("/api/v1/admin/crew/" + 555)
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content("""
                               {
                                   "email": "test@test.com",
                                   "firstname": "Test",
                                   "lastname": "Test",
                                   "birthDate": "1990-04-21",
                                   "employmentDate": "2020-04-21",
                                   "mkkDate": "2025-04-21"
                               }
                               """
                       )
               )
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Crew not found with id 555")
               );
    }

    @Test
    @SneakyThrows
    void remove() {
        mockMvc.perform(delete("/api/v1/admin/crew/" + marta.getId())
                       .with(csrf()))
               .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundStatusIfNoCrewFoundWhenRemove() {
        mockMvc.perform(delete("/api/v1/admin/crew/" + 555)
                       .with(csrf()))
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Crew not found with id 555")
               );
    }
}
