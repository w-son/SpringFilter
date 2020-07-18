package com.son.SpringFilter.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String hello() {
        return "Hello World!";
    }

}
