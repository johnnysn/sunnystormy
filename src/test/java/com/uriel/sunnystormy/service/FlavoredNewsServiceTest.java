package com.uriel.sunnystormy.service;

import com.uriel.sunnystormy.data.entity.FlavoredNews;
import com.uriel.sunnystormy.data.repository.FlavoredNewsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlavoredNewsServiceTest {

    @Mock
    private FlavoredNewsRepository repository;

    @InjectMocks
    private FlavoredNewsService flavoredNewsService;

    @Test
    void mustFindAllCorrectly() {
        // arrange
        var news = FlavoredNews.builder()
                .flavoredTitle("Spider Test")
                .flavoredContent("Spider test content")
                .flavor(FlavoredNews.Flavor.SUNNY)
                .build();
        var news2 = FlavoredNews.builder()
                .flavoredTitle("Scorpion Test")
                .flavoredContent("Scorpion test content")
                .flavor(FlavoredNews.Flavor.STORMY)
                .build();
        var pageable = PageRequest.of(0, 5);
        when(repository.findAll(any(Pageable.class))).thenReturn(
                new PageImpl<>(List.of(news, news2), pageable, 2)
        );
        // act
        var page = flavoredNewsService.findAll(pageable);
        // assert
        Assertions.assertTrue(page.getContent().contains(news));
        Assertions.assertTrue(page.getContent().contains(news2));
        Assertions.assertEquals(2, page.getTotalElements());
    }
}
