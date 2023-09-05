package com.uriel.sunnystormy.service.flavored;

import com.uriel.sunnystormy.data.entity.FlavoredNews;
import com.uriel.sunnystormy.data.entity.News;
import com.uriel.sunnystormy.data.repository.NewsRepository;
import com.uriel.sunnystormy.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GenerateFlavoredForLatestNewsServiceTest {

    @Mock
    private NewsRepository newsRepository;
    @Mock
    private GenerateFlavoredNewsService generateFlavoredNewsService;

    @InjectMocks
    private GenerateFlavoredForLatestNewsService subject;

    @Test
    void shouldGenerateCorrectly() {
        // arrange
        var news = News.builder().id(UUID.randomUUID()).build();
        when(newsRepository.findFirstByFlavoredNewsIsNullOrderByTimestampDesc()).thenReturn(Optional.of(news));
        var sunny = FlavoredNews.builder().flavor(FlavoredNews.Flavor.SUNNY).id(UUID.randomUUID()).build();
        var stormy = FlavoredNews.builder().flavor(FlavoredNews.Flavor.STORMY).id(UUID.randomUUID()).build();
        when(generateFlavoredNewsService.execute(news, FlavoredNews.Flavor.SUNNY)).thenReturn(sunny);
        when(generateFlavoredNewsService.execute(news, FlavoredNews.Flavor.STORMY)).thenReturn(stormy);
        // act
        var resultList = subject.execute();
        // assert
        assertTrue(resultList.contains(sunny));
        assertTrue(resultList.contains(stormy));
        assertEquals(2, resultList.size());
    }

    @Test
    void shouldThrowException_ifNoNewsToBeFlavored() {
        // arrange
        when(newsRepository.findFirstByFlavoredNewsIsNullOrderByTimestampDesc()).thenReturn(Optional.empty());
        // act & assert
        assertThrows(ResourceNotFoundException.class, () -> subject.execute());
    }
}