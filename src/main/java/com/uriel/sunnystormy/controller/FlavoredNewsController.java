package com.uriel.sunnystormy.controller;

import com.uriel.sunnystormy.controller.dto.FlavoredNewsOutDTO;
import com.uriel.sunnystormy.controller.mapper.FlavoredNewsMapper;
import com.uriel.sunnystormy.service.FlavoredNewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${news.provide.maximum-page-size}")
    private Integer maxPageSize;

    @GetMapping
    public Page<FlavoredNewsOutDTO> findAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        int pageNumber = page == null ? 0 : page;
        int pageSize = size == null ? maxPageSize : Math.min(size, maxPageSize);
        var pageable = PageRequest.of(pageNumber, pageSize, Sort.by("originalNews.date").descending());
        return service.findAll(pageable).map(mapper::entityToDto);
    }

}
