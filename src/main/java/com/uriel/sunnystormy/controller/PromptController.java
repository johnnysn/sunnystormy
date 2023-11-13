package com.uriel.sunnystormy.controller;

import com.uriel.sunnystormy.controller.dto.output.PromptOutDTO;
import com.uriel.sunnystormy.controller.mapper.PromptMapper;
import com.uriel.sunnystormy.service.prompt.PromptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prompt")
@RequiredArgsConstructor
@Tag(name = "Prompt Controller", description = "Endpoints to keep track of AI prompts")
public class PromptController {

    private final PromptService service;
    private final PromptMapper mapper;

    @GetMapping
    @Operation(
            summary = "Retrieves the latest prompts recorded in the database",
            description = """
                    The user can specify the page number.
                    """
    )
    public Page<PromptOutDTO> findAll(@RequestParam(required = false) Integer page) {
        var pageable = PageRequest.of(
                page == null ? 0 : page, 5,
                Sort.by("timestamp").descending()
        );
        return service.findAll(pageable).map(mapper::entityToDto);
    }

}
