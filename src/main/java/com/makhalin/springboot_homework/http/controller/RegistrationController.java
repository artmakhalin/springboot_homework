package com.makhalin.springboot_homework.http.controller;

import com.makhalin.springboot_homework.dto.CrewCreateEditDto;
import com.makhalin.springboot_homework.service.CrewService;
import com.makhalin.springboot_homework.validation.OnCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
@Validated
public class RegistrationController {

    private final CrewService crewService;

    @GetMapping()
    public String registration(Model model, CrewCreateEditDto crew) {
        model.addAttribute("crew", crew);

        return "crew/registration";
    }

    @PostMapping
    @Validated(OnCreate.class)
    public String create(@Valid CrewCreateEditDto crew) {
        crewService.create(crew);

        return "redirect:/login";
    }
}
