package com.uriel.sunnystormy.service;

import com.uriel.sunnystormy.data.entity.News;
import com.uriel.sunnystormy.data.repository.NewsRepository;
import com.uriel.sunnystormy.remote.NewsAPIRequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository repository;
    private final NewsAPIRequestHandler requestBuilder;

    public List<News> fetchLatestNews(int pageSize) {
        var news = requestBuilder.fetch(pageSize);
        news.forEach(repository::save);

        return news;
    }

    public Page<News> findAll(PageRequest pageable) {
        return repository.findAll(pageable);
    }
}
