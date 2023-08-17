package com.uriel.sunnystormy.controller;

import com.uriel.sunnystormy.dto.NewsOutDTO;
import com.uriel.sunnystormy.mapper.NewsMapper;
import com.uriel.sunnystormy.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService service;
    private final NewsMapper mapper;

    public NewsController(NewsService service, NewsMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<NewsOutDTO> listAll() {
        return service.listAll().stream().map(mapper::entityToDto).toList();
    }

}
