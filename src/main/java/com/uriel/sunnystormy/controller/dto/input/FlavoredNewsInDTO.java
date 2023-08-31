package com.uriel.sunnystormy.controller.dto.input;

import com.uriel.sunnystormy.data.entity.FlavoredNews;

import java.util.UUID;

public record FlavoredNewsInDTO(
        UUID news_id,
        FlavoredNews.Flavor flavor
) {
}
