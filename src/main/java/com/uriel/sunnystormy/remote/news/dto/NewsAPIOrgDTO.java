package com.uriel.sunnystormy.remote.news.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
public class NewsAPIOrgDTO {
    private String status;
    private Integer totalResults;
    private List<Article> articles;

    @Getter
    @Setter
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Article {
        private String author;
        private String title;
        private String description;
        private String urlToImage;
        private String publishedAt;
    }
}
