package com.uriel.sunnystormy.controller.mapper;

import com.uriel.sunnystormy.controller.dto.input.FlavoredNewsInDTO;
import com.uriel.sunnystormy.data.entity.FlavoredNews;
import com.uriel.sunnystormy.controller.dto.output.FlavoredNewsOutDTO;
import com.uriel.sunnystormy.service.news.NewsService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {NewsService.class})
public interface FlavoredNewsMapper {

    FlavoredNewsOutDTO entityToDto(FlavoredNews news);

    @Mapping(source = "news_id", target = "originalNews")
    FlavoredNews dtoToEntity(FlavoredNewsInDTO inDTO);

}
