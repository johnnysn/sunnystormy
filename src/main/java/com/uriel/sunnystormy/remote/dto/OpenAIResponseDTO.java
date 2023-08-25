package com.uriel.sunnystormy.remote.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OpenAIResponseDTO {

    private String model;

    private List<Choice> choices;

    @Getter
    @Setter
    public static class Choice {
        private Integer index;
        private OpenAIMessageDTO message;
    }

}
