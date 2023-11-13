package com.uriel.sunnystormy.controller;

import com.uriel.sunnystormy.configuration.NewsProperties;
import com.uriel.sunnystormy.controller.dto.output.FlavoredNewsOutDTO;
import com.uriel.sunnystormy.controller.dto.input.FlavoredNewsInDTO;
import com.uriel.sunnystormy.controller.mapper.FlavoredNewsMapper;
import com.uriel.sunnystormy.data.entity.FlavoredNews;
import com.uriel.sunnystormy.service.flavored.FlavoredNewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Flavored News Controller", description = "Endpoints related to AI prompting for flavoring news")
public class FlavoredNewsController {

    private final FlavoredNewsService service;
    private final FlavoredNewsMapper mapper;

    private final NewsProperties newsProperties;

    @GetMapping("/public/flavored-news")
    @Operation(
            summary = "Retrieves the latest flavored news existing in the database",
            description = """
                    The user can specify the page number, the page size (which must not exceed application.news.max-page-size) and the flavor (SUNNY or STORMY).
                    """
    )
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
    @Operation(
            summary = "Prompts the chat API for flavoring the specified news fragment",
            description = """
                    The resulting flavored news will be returned to the user and saved into the database.
                    
                    WARNING: this might consume your AI credits!
                    """
    )
    public FlavoredNewsOutDTO createFromPrompt(@RequestBody @Valid FlavoredNewsInDTO inDTO) {
        var flavoredNews = mapper.dtoToEntity(inDTO);
        return mapper.entityToDto(
                service.generate(flavoredNews.getOriginalNews(), flavoredNews.getFlavor())
        );
    }

    @PostMapping("/prompting/flavored-news/for-latest")
    @Operation(
            summary = "Prompts the chat API for flavoring the latest news fragment that has not been yet flavored",
            description = """
                    The resulting flavored news will be returned to the user and saved into the database.
                    
                    WARNING: this might consume your AI credits!
                    """
    )
    public List<FlavoredNewsOutDTO> createForLatestNews() {
        return service.generateForLatestNews().stream().map(mapper::entityToDto).toList();
    }

}
