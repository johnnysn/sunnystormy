package com.uriel.sunnystormy.remote.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OpenAIRequestDTO {

    private String model;

    private List<OpenAIMessageDTO> messages;

    private double temperature;

}
