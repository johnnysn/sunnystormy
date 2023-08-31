package com.uriel.sunnystormy.controller;

import com.uriel.sunnystormy.configuration.NewsProperties;
import com.uriel.sunnystormy.controller.dto.output.NewsOutDTO;
import com.uriel.sunnystormy.controller.mapper.NewsMapper;
import com.uriel.sunnystormy.service.news.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final NewsMapper newsMapper;

    private final NewsProperties properties;

    @GetMapping("/public/news")
    public Page<NewsOutDTO> findAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        int pageNumber = page == null ? 0 : page;
        int pageSize = size == null ? properties.maxPageSize() : Math.min(size, properties.maxPageSize());
        var pageable = PageRequest.of(pageNumber, pageSize, Sort.by("timestamp").descending());
        return newsService.findAll(pageable).map(newsMapper::entityToDto);
    }

    @PostMapping("/fetching/news")
    public List<NewsOutDTO> fetchLatest(@RequestParam(required = false) Integer size) {
        int batchSize = size == null ? properties.maxBatchSize() : Math.min(size, properties.maxBatchSize());
        return newsService.fetchLatestNews(batchSize).stream().map(newsMapper::entityToDto).toList();
    }

}
