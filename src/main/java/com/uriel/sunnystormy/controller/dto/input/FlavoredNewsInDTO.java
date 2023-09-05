package com.uriel.sunnystormy.controller.dto.input;

import com.uriel.sunnystormy.data.entity.FlavoredNews;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FlavoredNewsInDTO(
        @NotNull(message = "The news ID should not be null")
        UUID news_id,
        @NotNull(message = "The flavor shouldn't be bull")
        FlavoredNews.Flavor flavor
) {
}
