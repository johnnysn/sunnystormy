package com.uriel.sunnystormy.remote.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    public static class Article {
        private String author;
        private String title;
        private String description;
        private String urlToImage;
        private String publishedAt;
    }
}
