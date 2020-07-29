package com.perrine.portfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;


@Controller
public class AdminController {

    private static final String TEMPLATE_HOME = "index";

    @GetMapping("/")
    public String adminConnection() {
        return TEMPLATE_HOME;
    }

}


