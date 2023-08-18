package com.uriel.sunnystormy.service;

import com.uriel.sunnystormy.data.entity.News;
import com.uriel.sunnystormy.data.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository repository;

    public Page<News> findAll(PageRequest pageable) {
        return repository.findAll(pageable);
    }
}
