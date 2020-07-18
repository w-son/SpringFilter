package com.son.SpringFilter.controller;

import com.son.SpringFilter.domain.Faker;
import com.son.SpringFilter.service.FakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FakerController {

    private final FakerService fakerService;

    @GetMapping("/api/v1/faker")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Faker> findAll() {
        return fakerService.findAll();
    }

}
