package com.uriel.sunnystormy.remote.news;

import com.uriel.sunnystormy.configuration.NewsProperties;
import com.uriel.sunnystormy.data.entity.News;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NewsSelectionFilterTest {

    @Mock
    private NewsProperties newsProperties;

    @InjectMocks
    private NewsSelectionFilter subject;

    @Test
    void shouldSelectCorrectly() {
        // arrange
        var newsOk1 = News.builder().id(UUID.randomUUID()).content("1 2 3 4 5 6 7").build();
        var newsTooLong = News.builder().id(UUID.randomUUID()).content("1 2 3 4 5 6 7 8 9").build();
        var newsOk2 = News.builder().id(UUID.randomUUID()).content("1 2 3 4").build();
        var newsTooShort = News.builder().id(UUID.randomUUID()).content("1 2").build();
        Mockito.when(newsProperties.maxWordCount()).thenReturn(7);
        // act
        var result = subject.select(
                List.of(newsTooLong, newsOk1, newsOk2, newsTooShort).stream(), 2
        );
        // assert
        assertFalse(result.contains(newsTooLong));
        assertTrue(result.contains(newsOk2));
        assertTrue(result.contains(newsOk1));
        assertFalse(result.contains(newsTooShort));
    }
}