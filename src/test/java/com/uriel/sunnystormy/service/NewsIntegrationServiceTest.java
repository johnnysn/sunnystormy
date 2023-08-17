package com.uriel.sunnystormy.service;

import com.uriel.sunnystormy.Application;
import com.uriel.sunnystormy.data.entity.News;
import com.uriel.sunnystormy.data.repository.NewsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest(classes = Application.class)
public class NewsIntegrationServiceTest {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsService newsService;

    @Test
    void mustRetrieveAllRecordedNews() {
        // arrange
        var news = News.builder()
                .title("Spider Test")
                .content("Spider test content")
                .date(LocalDate.now())
                .build();
        var news2 = News.builder()
                .title("Scorpion Test")
                .content("Scorpion test content")
                .date(LocalDate.now())
                .build();
        newsRepository.save(news);
        newsRepository.save(news2);
        // act
        var result = newsService.listAll();
        // assert
        Assertions.assertTrue(result.contains(news));
        Assertions.assertTrue(result.contains(news2));
        Assertions.assertEquals(2, result.size());
    }
}
