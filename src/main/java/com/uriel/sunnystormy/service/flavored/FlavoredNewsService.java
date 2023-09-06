package com.uriel.sunnystormy.service.flavored;

import com.uriel.sunnystormy.data.entity.FlavoredNews;
import com.uriel.sunnystormy.data.entity.News;
import com.uriel.sunnystormy.data.repository.FlavoredNewsRepository;
import com.uriel.sunnystormy.data.specification.FlavoredNewsFilter;
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

    public Page<FlavoredNews> findAll(FlavoredNews.Flavor flavor, PageRequest pageable) {
        var filter = FlavoredNewsFilter.builder().flavor(flavor).build();
        return repository.findAll(filter, pageable);
    }

    public FlavoredNews generate(News news, FlavoredNews.Flavor flavor) {
        return generateFlavoredNewsService.execute(news, flavor);
    }

    public List<FlavoredNews> generateForLatestNews() {
        return generateFlavoredForLatestNewsService.execute();
    }
}
