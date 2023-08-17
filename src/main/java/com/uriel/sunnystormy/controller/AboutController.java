package com.uriel.sunnystormy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/about")
public class AboutController {

    @GetMapping
    public ResponseEntity<String> about() {
        return ResponseEntity.ok("Sunny vs Stormy API is up and running!");
    }

}
