package com.uriel.sunnystormy.remote.news;

import com.uriel.sunnystormy.data.entity.News;
import com.uriel.sunnystormy.remote.news.dto.NewsAPIOrgDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

@Component
public class NewsAPIOrgMapper {

    public News map(NewsAPIOrgDTO.Article article) {
        var publishedAt = article.getPublishedAt();
        LocalDateTime date;
        if (publishedAt != null && publishedAt.indexOf("T") >= 0) {
            try {
                if (publishedAt.indexOf("Z") >= 0) {
                    var zoned = ZonedDateTime.parse(publishedAt);
                    date = zoned.toLocalDateTime();
                } else {
                    date = LocalDateTime.parse(publishedAt);
                }
            } catch (DateTimeParseException e) {
                return null;
            }
        } else {
            return null;
        }

        return News.builder()
                .title(article.getTitle())
                .content(article.getDescription())
                .imgUrl(article.getUrlToImage())
                .timestamp(date)
                .build();
    }

}
