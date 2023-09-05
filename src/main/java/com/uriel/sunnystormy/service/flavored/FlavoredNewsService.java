package com.uriel.sunnystormy.service.flavored;

import com.uriel.sunnystormy.data.entity.FlavoredNews;
import com.uriel.sunnystormy.data.entity.News;
import com.uriel.sunnystormy.data.repository.FlavoredNewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlavoredNewsService {

    private final FlavoredNewsRepository repository;
    private final GenerateFlavoredNewsService generateFlavoredNewsService;
    private final GenerateFlavoredForLatestNewsService generateFlavoredForLatestNewsService;

    public Page<FlavoredNews> findAll(PageRequest pageable) {
        return repository.findAll(pageable);
    }

    public FlavoredNews generate(News news, FlavoredNews.Flavor flavor) {
        return generateFlavoredNewsService.execute(news, flavor);
    }

    public List<FlavoredNews> generateForLatestNews() {
        return generateFlavoredForLatestNewsService.execute();
    }
}
