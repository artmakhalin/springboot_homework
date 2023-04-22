package com.makhalin.springboot_homework.http.rest;

import com.makhalin.springboot_homework.dto.CrewCreateEditDto;
import com.makhalin.springboot_homework.dto.CrewReadDto;
import com.makhalin.springboot_homework.service.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegistrationRestController {

    private final CrewService crewService;

    @PostMapping
    public ResponseEntity<CrewReadDto> create(@Valid @RequestBody CrewCreateEditDto crew) {
        return new ResponseEntity<>(crewService.create(crew), HttpStatus.CREATED);
    }
}
