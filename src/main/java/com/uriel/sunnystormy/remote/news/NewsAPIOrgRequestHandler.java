package com.uriel.sunnystormy.remote.news;

import com.uriel.sunnystormy.data.entity.News;
import com.uriel.sunnystormy.remote.news.dto.NewsAPIOrgDTO;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class NewsAPIOrgRequestHandler implements NewsAPIRequestHandler {
    private final String apiKey;

    private final WebClient client;

    @Autowired
    @Setter
    private NewsSelectionFilter selectionFilter;

    @Autowired
    @Setter
    private NewsAPIOrgMapper mapper;

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

        var newsStream = response.getArticles().stream()
                .filter(a -> a.getDescription() != null && !a.getDescription().isBlank())
                .filter(a -> a.getTitle() != null && !a.getTitle().isBlank())
                .map(mapper::map)
                .filter(Objects::nonNull);

        return selectionFilter.select(newsStream, pageSize);
    }



}
