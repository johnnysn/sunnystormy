package com.uriel.sunnystormy.controller.mapper;

import com.uriel.sunnystormy.controller.dto.NewsOutDTO;
import com.uriel.sunnystormy.data.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface NewsMapper {

    NewsOutDTO entityToDto(News news);

}
