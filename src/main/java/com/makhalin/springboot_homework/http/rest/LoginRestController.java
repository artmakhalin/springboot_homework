package com.makhalin.springboot_homework.http.rest;

import com.makhalin.springboot_homework.dto.AuthRequest;
import com.makhalin.springboot_homework.dto.CrewReadDto;
import com.makhalin.springboot_homework.service.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.makhalin.springboot_homework.exception.BadRequestException.badRequestException;

@RestController
@RequestMapping("/api/v1/login")
@RequiredArgsConstructor
public class LoginRestController {

    private final CrewService crewService;

    @PostMapping
    public ResponseEntity<CrewReadDto> login(@Valid @RequestBody AuthRequest authRequest,
                                             HttpServletRequest request) {
        try {
            request.login(authRequest.getUsername(), authRequest.getPassword());

            return ResponseEntity.ok()
                                 .body(crewService.findByEmail(authRequest.getUsername()));
        } catch (ServletException e) {
            throw badRequestException("Bad credentials");
        }
    }
}
