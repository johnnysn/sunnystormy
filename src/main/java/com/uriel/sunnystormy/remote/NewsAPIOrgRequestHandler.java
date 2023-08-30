package com.uriel.sunnystormy.remote;

import com.uriel.sunnystormy.data.entity.News;
import com.uriel.sunnystormy.remote.dto.NewsAPIOrgDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class NewsAPIOrgRequestHandler implements NewsAPIRequestHandler {
    private final String apiKey;

    private final WebClient client;

    public NewsAPIOrgRequestHandler(String baseUrl, String apiKey) {
        this.apiKey = apiKey;
        client = WebClient.create(baseUrl);
    }

    @Override
    public List<News> fetch(int pageSize) {
        var response = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/top-headlines")
                        .queryParam("apiKey", apiKey)
                        .queryParam("pageSize", pageSize*2)
                        .queryParam("country", "us")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        status -> status != HttpStatus.OK,
                        clientResponse -> Mono.empty()
                ).bodyToMono(NewsAPIOrgDTO.class)
                .block();

        if (response == null) {
            return Collections.emptyList();
        }

        return response.getArticles().stream()
                .filter(a -> a.getDescription() != null && !a.getDescription().isBlank())
                .filter(a -> a.getTitle() != null && !a.getTitle().isBlank())
                .map(this::mapToNews)
                .filter(Objects::nonNull)
                .limit(pageSize)
                .toList();
    }

    private News mapToNews(NewsAPIOrgDTO.Article article) {
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
