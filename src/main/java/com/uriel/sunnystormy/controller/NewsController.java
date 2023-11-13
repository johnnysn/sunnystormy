package com.uriel.sunnystormy.controller;

import com.uriel.sunnystormy.configuration.NewsProperties;
import com.uriel.sunnystormy.controller.dto.output.NewsOutDTO;
import com.uriel.sunnystormy.controller.mapper.NewsMapper;
import com.uriel.sunnystormy.service.news.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "News Controller", description = "Endpoints for fetching news from an external API to the local database")
public class NewsController {

    private final NewsService newsService;
    private final NewsMapper newsMapper;

    private final NewsProperties properties;

    @GetMapping("/public/news")
    @Operation(
            summary = "Retrieves news from the local database",
            description = """
                    The user can specify the page number and the page size (which must no exceed application.news.max-page-size).
                    """
    )
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
    @Operation(
            summary = "Consumes latest news from external API",
            description = """
                Gets latest news from external API and records them into the database. The application is configured to fetch a predefined maximum number of items per day (application.news.max-batch-size). 
                    
                The optional parameter 'size' specifies the number of news fragments to be retrieved and will be ignored if it exceeds the maximum configured batch size.
                    """
    )
    public List<NewsOutDTO> fetchLatest(@RequestParam(required = false) Integer size) {
        int batchSize = size == null ? properties.maxBatchSize() : Math.min(size, properties.maxBatchSize());
        return newsService.fetchLatestNews(batchSize).stream().map(newsMapper::entityToDto).toList();
    }

}
