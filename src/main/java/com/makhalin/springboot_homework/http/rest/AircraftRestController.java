package com.makhalin.springboot_homework.http.rest;

import com.makhalin.springboot_homework.dto.AircraftCreateEditDto;
import com.makhalin.springboot_homework.dto.AircraftReadDto;
import com.makhalin.springboot_homework.service.AircraftService;
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
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/aircraft")
@RequiredArgsConstructor
public class AircraftRestController {

    private final AircraftService aircraftService;

    @GetMapping
    public ResponseEntity<List<AircraftReadDto>> findAll() {
        return ResponseEntity.ok()
                             .body(aircraftService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AircraftReadDto> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok()
                             .body(aircraftService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AircraftReadDto> create(@Valid @RequestBody AircraftCreateEditDto aircraft) {
        return new ResponseEntity<>(aircraftService.create(aircraft), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AircraftReadDto> update(@PathVariable("id") Integer id,
                                                  @Valid @RequestBody AircraftCreateEditDto aircraft) {
        return ResponseEntity.ok()
                             .body(aircraftService.update(id, aircraft));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        aircraftService.delete(id);

        return ResponseEntity.noContent()
                             .build();
    }
}
