package com.uriel.sunnystormy.controller;

import com.uriel.sunnystormy.configuration.NewsProperties;
import com.uriel.sunnystormy.controller.dto.output.FlavoredNewsOutDTO;
import com.uriel.sunnystormy.controller.dto.input.FlavoredNewsInDTO;
import com.uriel.sunnystormy.controller.mapper.FlavoredNewsMapper;
import com.uriel.sunnystormy.data.entity.FlavoredNews;
import com.uriel.sunnystormy.service.flavored.FlavoredNewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FlavoredNewsController {

    private final FlavoredNewsService service;
    private final FlavoredNewsMapper mapper;

    private final NewsProperties newsProperties;

    @GetMapping("/public/flavored-news")
    public Page<FlavoredNewsOutDTO> findAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) FlavoredNews.Flavor flavor
            ) {
        int pageNumber = page == null ? 0 : page;
        int pageSize = size == null ? newsProperties.maxPageSize() : Math.min(size, newsProperties.maxPageSize());
        var pageable = PageRequest.of(pageNumber, pageSize, Sort.by("originalNews.timestamp").descending());
        return service.findAll(flavor, pageable).map(mapper::entityToDto);
    }

    @PostMapping("/prompting/flavored-news")
    public FlavoredNewsOutDTO createFromPrompt(@RequestBody @Valid FlavoredNewsInDTO inDTO) {
        var flavoredNews = mapper.dtoToEntity(inDTO);
        return mapper.entityToDto(
                service.generate(flavoredNews.getOriginalNews(), flavoredNews.getFlavor())
        );
    }

    @PostMapping("/prompting/flavored-news/for-latest")
    public List<FlavoredNewsOutDTO> createForLatestNews() {
        return service.generateForLatestNews().stream().map(mapper::entityToDto).toList();
    }

}
