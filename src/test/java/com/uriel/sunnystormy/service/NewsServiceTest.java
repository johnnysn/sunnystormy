package com.uriel.sunnystormy.service;

import com.uriel.sunnystormy.data.entity.News;
import com.uriel.sunnystormy.data.repository.NewsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private NewsRepository repository;

    @InjectMocks
    private NewsService newsService;

    @Test
    void mustFindAllCorrectly() {
        // arrange
        var news = News.builder()
                .title("Spider Test")
                .content("Spider test content")
                .flavor(News.Flavor.SUNNY)
                .date(LocalDate.now())
                .build();
        var news2 = News.builder()
                .title("Scorpion Test")
                .content("Scorpion test content")
                .flavor(News.Flavor.STORMY)
                .date(LocalDate.now())
                .build();
        var pageable = PageRequest.of(0, 5);
        when(repository.findAll(any(Pageable.class))).thenReturn(
                new PageImpl<>(List.of(news, news2), pageable, 2)
        );
        // act
        var page = newsService.findAll(pageable);
        // assert
        Assertions.assertTrue(page.getContent().contains(news));
        Assertions.assertTrue(page.getContent().contains(news2));
        Assertions.assertEquals(2, page.getTotalElements());
    }
}
