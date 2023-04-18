package com.makhalin.springboot_homework.http.controller;

import com.makhalin.springboot_homework.dto.CrewCreateEditDto;
import com.makhalin.springboot_homework.service.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/crew")
@RequiredArgsConstructor
public class CrewController {

    private final CrewService crewService;

    @GetMapping("/registration")
    public String registration(Model model, CrewCreateEditDto crew) {
        model.addAttribute("crew", crew);

        return "crew/registration";
    }

    @PostMapping
    public String create(@Valid CrewCreateEditDto crew) {
        crewService.create(crew);

        return "redirect:/login";
    }
}
