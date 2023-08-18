package com.uriel.sunnystormy.controller;

import com.uriel.sunnystormy.dto.NewsOutDTO;
import com.uriel.sunnystormy.mapper.NewsMapper;
import com.uriel.sunnystormy.service.NewsService;
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
public class NewsController {

    private final NewsService service;
    private final NewsMapper mapper;

    @GetMapping
    public Page<NewsOutDTO> findAll(@RequestParam(required = false) Integer page) {
        var pageable = PageRequest.of(page == null ? 0 : page, 5, Sort.by("date").descending());
        return service.findAll(pageable).map(mapper::entityToDto);
    }

}
