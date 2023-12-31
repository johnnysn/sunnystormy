package com.uriel.sunnystormy.controller.dto.output;

import com.uriel.sunnystormy.data.entity.FlavoredNews;

import java.util.UUID;

public record FlavoredNewsOutDTO(
        UUID id,
        String flavoredTitle,
        String flavoredContent,
        FlavoredNews.Flavor flavor,
        NewsOutDTO originalNews
) {
}
