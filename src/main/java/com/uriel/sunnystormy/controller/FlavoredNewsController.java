package com.uriel.sunnystormy.controller;

import com.uriel.sunnystormy.dto.FlavoredNewsOutDTO;
import com.uriel.sunnystormy.mapper.FlavoredNewsMapper;
import com.uriel.sunnystormy.service.FlavoredNewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class FlavoredNewsController {

    private final FlavoredNewsService service;
    private final FlavoredNewsMapper mapper;

    @GetMapping
    public Page<FlavoredNewsOutDTO> findAll(@RequestParam(required = false) Integer page) {
        var pageable = PageRequest.of(page == null ? 0 : page, 5, Sort.by("originalNews.date").descending());
        return service.findAll(pageable).map(mapper::entityToDto);
    }

}
