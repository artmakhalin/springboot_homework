package com.makhalin.springboot_homework.http.rest;

import com.makhalin.springboot_homework.dto.AirportCreateEditDto;
import com.makhalin.springboot_homework.dto.AirportFilter;
import com.makhalin.springboot_homework.dto.AirportReadDto;
import com.makhalin.springboot_homework.dto.PageResponse;
import com.makhalin.springboot_homework.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/v1/admin/airports")
@RequiredArgsConstructor
public class AirportRestController {

    private final AirportService airportService;

    @GetMapping
    public PageResponse<AirportReadDto> findAll(AirportFilter filter, Pageable pageable) {
        return PageResponse.of(airportService.findAll(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportReadDto> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok()
                             .body(airportService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AirportReadDto> create(@Valid @RequestBody AirportCreateEditDto airport) {
        return new ResponseEntity<>(airportService.create(airport), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirportReadDto> update(@PathVariable("id") Integer id,
                                                 @Valid @RequestBody AirportCreateEditDto airport) {
        return ResponseEntity.ok()
                             .body(airportService.update(id, airport));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        airportService.delete(id);

        return ResponseEntity.noContent()
                             .build();
    }
}
