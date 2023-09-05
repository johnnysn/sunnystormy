package com.uriel.sunnystormy.service.flavored;

import com.uriel.sunnystormy.data.entity.FlavoredNews;
import com.uriel.sunnystormy.data.entity.News;
import com.uriel.sunnystormy.data.repository.NewsRepository;
import com.uriel.sunnystormy.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class GenerateFlavoredForLatestNewsService {

    private final NewsRepository newsRepository;
    private final GenerateFlavoredNewsService generateFlavoredNewsService;

    List<FlavoredNews> execute() {
        News news = newsRepository.findFirstByFlavoredNewsIsNullOrderByTimestampDesc().orElseThrow(
                () -> new ResourceNotFoundException("There are no news to be flavored.")
        );

        var sunny = generateFlavoredNewsService.execute(news, FlavoredNews.Flavor.SUNNY);
        var stormy = generateFlavoredNewsService.execute(news, FlavoredNews.Flavor.STORMY);

        return List.of(sunny, stormy);
    }

}
