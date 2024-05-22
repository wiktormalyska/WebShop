package com.wiktormalyska.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class AdminController {
    @GetMapping("/admin/adminpanel")
    public String adminpanel() {
        return "adminpanel";
    }

}
