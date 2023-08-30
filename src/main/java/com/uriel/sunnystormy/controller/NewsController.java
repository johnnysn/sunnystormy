package com.uriel.sunnystormy.controller;

import com.uriel.sunnystormy.controller.dto.NewsOutDTO;
import com.uriel.sunnystormy.controller.mapper.NewsMapper;
import com.uriel.sunnystormy.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final NewsMapper newsMapper;

    @Value("${application.news.provide.maximum-page-size}")
    private Integer maxPageSize;

    @GetMapping("/public/news")
    public Page<NewsOutDTO> findAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        int pageNumber = page == null ? 0 : page;
        int pageSize = size == null ? maxPageSize : Math.min(size, maxPageSize);
        var pageable = PageRequest.of(pageNumber, pageSize, Sort.by("date").descending());
        return newsService.findAll(pageable).map(newsMapper::entityToDto);
    }

}
