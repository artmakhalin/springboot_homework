package com.makhalin.springboot_homework.http.rest;

import com.makhalin.springboot_homework.dto.CrewAircraftCreateEditDto;
import com.makhalin.springboot_homework.dto.CrewAircraftReadDto;
import com.makhalin.springboot_homework.service.CrewAircraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin/crewAircraft")
@RequiredArgsConstructor
public class CrewAircraftRestController {

    private final CrewAircraftService crewAircraftService;

    @GetMapping("/{id}")
    public ResponseEntity<CrewAircraftReadDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                             .body(crewAircraftService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CrewAircraftReadDto> create(@Valid @RequestBody CrewAircraftCreateEditDto crewAircraft) {
        return new ResponseEntity<>(crewAircraftService.create(crewAircraft), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CrewAircraftReadDto> update(@PathVariable("id") Long id,
                                                      @Valid @RequestBody CrewAircraftCreateEditDto crewAircraft) {
        return ResponseEntity.ok()
                             .body(crewAircraftService.update(id, crewAircraft));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        crewAircraftService.delete(id);

        return ResponseEntity.noContent()
                             .build();
    }
}
