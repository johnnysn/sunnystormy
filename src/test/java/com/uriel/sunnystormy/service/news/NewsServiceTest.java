package com.uriel.sunnystormy.service.news;

import com.uriel.sunnystormy.data.entity.News;
import com.uriel.sunnystormy.data.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;
    @Mock
    private FetchLatestNewsService fetchLatestNewsService;

    @InjectMocks
    private NewsService service;

    @Test
    void shouldFindAllCorrectly() {
        // arrange
        var pageSize = 5;
        var pageRequest = PageRequest.of(0, pageSize);
        var news1 = News.builder().build();
        var news2 = News.builder().build();
        var page = new PageImpl<>(List.of(news1, news2), pageRequest, 2);
        when(newsRepository.findAll(pageRequest)).thenReturn(
                page
        );
        // act
        var result = service.findAll(pageRequest);
        // assert
        assertSame(page, result);
    }

    @Test
    void shouldCallFetchService() {
        // arrange
        var batchSize = 5;
        var news = List.of(News.builder().build());
        when(fetchLatestNewsService.execute(batchSize)).thenReturn(news);
        // act
        var result = service.fetchLatestNews(batchSize);
        // assert
        verify(fetchLatestNewsService).execute(batchSize);
        assertSame(news, result);
    }
}