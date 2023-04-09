package com.enoca.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api-docs")
public class ApiDocsController {
    @GetMapping
    public String getApiDocs() {
        return "api-docs/api-docs";
    }
}
