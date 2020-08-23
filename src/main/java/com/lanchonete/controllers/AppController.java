package com.lanchonete.controllers;

import java.util.Objects;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/app")
public class AppController {

    @GetMapping()
    public Object getCep() {
    
        return Objects.nonNull(System.getenv("ambiente")) ? "dev" : "test";
    }
}
