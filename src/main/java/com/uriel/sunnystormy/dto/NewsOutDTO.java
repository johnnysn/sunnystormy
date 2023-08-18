package com.uriel.sunnystormy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uriel.sunnystormy.data.entity.News;

import java.time.LocalDate;
import java.util.UUID;

public record NewsOutDTO(
        UUID id,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        String title,
        String content,
        News.Flavor flavor
) {
}
