package com.example.biblioteka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class MainController {
    @GetMapping("/api/v1")
    public String homePage(Model model) {
        return "homepage";
    }
}
