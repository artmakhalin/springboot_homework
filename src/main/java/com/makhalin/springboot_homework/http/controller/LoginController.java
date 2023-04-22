package com.makhalin.springboot_homework.http.controller;

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

    @GetMapping("/login")
    public String loginPage() {
        return "crew/login";
    }

    @GetMapping("/main")
    public String mainPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("crew", crewService.findByEmail(userDetails.getUsername()));
        
        return "crew/main";
    }
}
