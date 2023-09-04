package com.uriel.sunnystormy.remote.chat.dto;

import java.util.List;

public record OpenAIRequestDTO(String model, List<OpenAIMessageDTO> messages, double temperature) {
}
