package com.uriel.sunnystormy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/about")
@Tag(name = "About Controller", description = "Basic info about the application")
public class AboutController {

    @GetMapping
    @Operation(summary = "Used to check if the API is up and running")
    public ResponseEntity<String> about() {
        return ResponseEntity.ok("Sunny vs Stormy API is up and running!");
    }

}
