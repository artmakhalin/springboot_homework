package com.makhalin.springboot_homework.http.controller;

import com.makhalin.springboot_homework.dto.CrewCreateEditDto;
import com.makhalin.springboot_homework.dto.CrewFilter;
import com.makhalin.springboot_homework.dto.PageResponse;
import com.makhalin.springboot_homework.service.CrewAircraftService;
import com.makhalin.springboot_homework.service.CrewService;
import com.makhalin.springboot_homework.validation.OnUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/crew")
@RequiredArgsConstructor
@Validated
public class CrewController {

    private final CrewService crewService;
    private final CrewAircraftService crewAircraftService;

    @GetMapping
    public String findAllByFilter(Model model, CrewFilter filter, Pageable pageable) {
        var page = crewService.findAllByFilter(filter, pageable);
        model.addAttribute("crewList", PageResponse.of(page));
        model.addAttribute("filter", filter);

        return "crew/crewList";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("crew", crewService.findById(id));
        model.addAttribute("crewAircraftList", crewAircraftService.findAllByCrewId(id));

        return "crew/crew";
    }

    @PostMapping("/{id}/update")
    @Validated(OnUpdate.class)
    public String update(@PathVariable("id") Integer id, @Valid CrewCreateEditDto crew) {
        crewService.update(id, crew);

        return "redirect:/crew/{id}";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        crewService.delete(id);

        return "redirect:/crew";
    }
}
