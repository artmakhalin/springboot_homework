package com.makhalin.springboot_homework.http.controller;

import com.makhalin.springboot_homework.service.CrewAircraftService;
import com.makhalin.springboot_homework.service.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final CrewService crewService;
    private final CrewAircraftService crewAircraftService;

    @GetMapping("/login")
    public String loginPage() {
        return "crew/login";
    }

    @GetMapping("/main")
    public String mainPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        var crew = crewService.findByEmail(userDetails.getUsername());
        model.addAttribute("crew", crew);
        model.addAttribute("crewAircraftList", crewAircraftService.findAllByCrewId(crew.getId()));
        
        return "crew/main";
    }
}
