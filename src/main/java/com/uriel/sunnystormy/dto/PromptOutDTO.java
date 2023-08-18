package com.uriel.sunnystormy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record PromptOutDTO(
        UUID id,
        String content,
        String response,
        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss") LocalDateTime timestamp
) {
}
