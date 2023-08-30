package com.uriel.sunnystormy.service.news;

import com.uriel.sunnystormy.data.entity.News;
import com.uriel.sunnystormy.data.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository repository;
    private final FetchLatestNewsService fetchLatestNewsService;

    public List<News> fetchLatestNews(int batchSize) {
        return fetchLatestNewsService.execute(batchSize);
    }

    public Page<News> findAll(PageRequest pageable) {
        return repository.findAll(pageable);
    }
}
