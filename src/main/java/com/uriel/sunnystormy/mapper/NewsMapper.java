package com.uriel.sunnystormy.mapper;

import com.uriel.sunnystormy.data.entity.News;
import com.uriel.sunnystormy.dto.NewsOutDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    NewsOutDTO entityToDto(News news);

}
