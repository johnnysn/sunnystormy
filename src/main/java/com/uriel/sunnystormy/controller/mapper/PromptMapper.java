package com.uriel.sunnystormy.controller.mapper;

import com.uriel.sunnystormy.data.entity.Prompt;
import com.uriel.sunnystormy.controller.dto.PromptOutDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PromptMapper {

    PromptOutDTO entityToDto(Prompt prompt);

}
