package com.uriel.sunnystormy.service;

import com.uriel.sunnystormy.data.entity.News;
import com.uriel.sunnystormy.data.repository.NewsRepository;
import com.uriel.sunnystormy.remote.NewsAPIRequestHandler;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;
    @Mock
    private NewsAPIRequestHandler requestBuilder;

    @InjectMocks
    private NewsService service;

    @Test
    void shouldFetchCorrectly() {
        // arrange
        int pageSize = 5;
        var news1 = News.builder().id(UUID.randomUUID()).build();
        var news2 = News.builder().id(UUID.randomUUID()).build();
        when(requestBuilder.fetch(anyInt())).thenReturn(List.of(news1, news2));
        // act
        var result = service.fetchLatestNews(pageSize);
        // assert
        assertEquals(2, result.size());
        verify(requestBuilder).fetch(pageSize);
        verify(newsRepository).save(news1);
        verify(newsRepository).save(news2);
    }
}