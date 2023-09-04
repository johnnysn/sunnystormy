package com.uriel.sunnystormy.remote.chat.dto;

import java.util.List;

public record OpenAIResponseDTO(String model, List<Choice> choices) {

    public record Choice(Integer index, OpenAIMessageDTO message) {
    }
}
