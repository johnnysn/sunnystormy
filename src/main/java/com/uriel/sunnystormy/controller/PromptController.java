package com.uriel.sunnystormy.controller;

import com.uriel.sunnystormy.controller.dto.PromptOutDTO;
import com.uriel.sunnystormy.controller.mapper.PromptMapper;
import com.uriel.sunnystormy.service.prompt.PromptService;
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
public class PromptController {

    private final PromptService service;
    private final PromptMapper mapper;

    @GetMapping
    public Page<PromptOutDTO> findAll(@RequestParam(required = false) Integer page) {
        var pageable = PageRequest.of(
                page == null ? 0 : page, 5,
                Sort.by("timestamp").descending()
        );
        return service.findAll(pageable).map(mapper::entityToDto);
    }

}
