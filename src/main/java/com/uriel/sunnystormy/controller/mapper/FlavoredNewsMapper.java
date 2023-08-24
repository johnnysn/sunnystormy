package com.uriel.sunnystormy.controller.mapper;

import com.uriel.sunnystormy.data.entity.FlavoredNews;
import com.uriel.sunnystormy.controller.dto.FlavoredNewsOutDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlavoredNewsMapper {

    FlavoredNewsOutDTO entityToDto(FlavoredNews news);

}
