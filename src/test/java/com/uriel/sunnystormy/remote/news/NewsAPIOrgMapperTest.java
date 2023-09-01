package com.uriel.sunnystormy.remote.news;

import com.uriel.sunnystormy.remote.news.dto.NewsAPIOrgDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NewsAPIOrgMapperTest {

    @InjectMocks
    private NewsAPIOrgMapper subject;

    @Test
    void shouldMapCorrectly() {
        // arrange
        var article = NewsAPIOrgDTO.Article.builder()
                .title("Titulo")
                .description("Description")
                .publishedAt("2004-05-12T12:02:01Z")
                .urlToImage("urltoimage")
                .build();
        // act
        var result = subject.map(article);
        // assert
        assertEquals(article.getTitle(), result.getTitle());
        assertEquals(article.getDescription(), result.getContent());
        assertEquals(article.getUrlToImage(), result.getImgUrl());
        assertNotNull(result.getTimestamp());
    }

    @Test
    void shouldReturnNullIfDateIsInvalid() {
        // arrange
        var article = NewsAPIOrgDTO.Article.builder()
                .title("Titulo 2")
                .description("Description 2")
                .publishedAt("2004-05-12T12:02:057684646Y")
                .urlToImage("urltoimage2")
                .build();
        // act
        var result = subject.map(article);
        // assert
        assertNull(result);
    }
}