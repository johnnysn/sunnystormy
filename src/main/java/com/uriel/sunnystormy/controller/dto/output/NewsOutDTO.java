package com.uriel.sunnystormy.controller.dto.output;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record NewsOutDTO(
        UUID id,
        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
        LocalDateTime timestamp,
        String title,
        String content,
        String imgUrl
) {
}
