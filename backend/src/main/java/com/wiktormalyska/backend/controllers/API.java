package com.wiktormalyska.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class API {
    @GetMapping("/api")
    public String apiCall(){
        return "API call";
    }
}
