package com.uriel.sunnystormy.service.news;

import com.uriel.sunnystormy.configuration.NewsProperties;
import com.uriel.sunnystormy.data.entity.News;
import com.uriel.sunnystormy.data.repository.NewsRepository;
import com.uriel.sunnystormy.remote.NewsAPIRequestHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FetchLatestNewsServiceTest {

    @Mock
    private NewsRepository newsRepository;
    @Mock
    private NewsAPIRequestHandler requestBuilder;
    @Mock
    private NewsProperties newsProperties;

    @InjectMocks
    private FetchLatestNewsService subject;

    @BeforeEach
    void setUp() {
        when(newsProperties.maxBatchSize()).thenReturn(500);
    }

    @Test
    void shouldFetchCorrectly() {
        // arrange
        int batchSize = 5;
        int preFetchedTodayNewsCount = 2;
        // There are already 2 news entries for today
        when(newsRepository.countNewsStartingFrom(any(LocalDateTime.class))).thenReturn(preFetchedTodayNewsCount);
        var now = LocalDateTime.now();
        var alreadyFetchedNewsTime = now.minusHours(5);
        var news1 = getNews("Repeated News", alreadyFetchedNewsTime);
        var news2 = getNews("New news 1", now.minusHours(3));
        var news3 = getNews("New news 2", now.minusHours(1));
        when(requestBuilder.fetch(anyInt())).thenReturn(List.of(news1, news2, news3));
        when(newsRepository.existsByTitleLikeIgnoreCaseAndTimestamp(news1.getTitle(), alreadyFetchedNewsTime)).thenReturn(true);
        // act
        var result = subject.execute(batchSize);
        // assert
        assertEquals(2, result.size());
        assertTrue(result.contains(news2));
        assertTrue(result.contains(news3));
        verify(requestBuilder).fetch(batchSize - preFetchedTodayNewsCount);
        verify(newsRepository).save(news2);
        verify(newsRepository).save(news3);
        verify(newsRepository, never()).save(news1);
    }

    private static News getNews(String title, LocalDateTime time) {
        return News.builder()
                .id(UUID.randomUUID())
                .title(title)
                .timestamp(time)
                .build();
    }
}