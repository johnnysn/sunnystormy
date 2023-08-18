package com.uriel.sunnystormy.mapper;

import com.uriel.sunnystormy.data.entity.Prompt;
import com.uriel.sunnystormy.dto.PromptOutDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PromptMapper {

    PromptOutDTO entityToDto(Prompt prompt);

}
