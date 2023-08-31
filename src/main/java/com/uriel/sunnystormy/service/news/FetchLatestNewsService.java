package com.uriel.sunnystormy.service.news;

import com.uriel.sunnystormy.configuration.NewsProperties;
import com.uriel.sunnystormy.data.entity.News;
import com.uriel.sunnystormy.data.repository.NewsRepository;
import com.uriel.sunnystormy.remote.news.NewsAPIRequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
class FetchLatestNewsService {

    private final NewsRepository repository;
    private final NewsAPIRequestHandler requestBuilder;

    private final NewsProperties properties;

    List<News> execute(int batchSize) {
        int requestBatchSize = countRemainingNewsForToday(Math.min(batchSize, properties.maxBatchSize()));

        if (requestBatchSize <= 0)
            return Collections.emptyList();

        var news = requestBuilder.fetch(requestBatchSize).stream()
                .filter(Predicate.not(
                        n -> repository.existsByTitleLikeIgnoreCaseAndTimestamp(n.getTitle().trim(), n.getTimestamp())
                ))
                .toList();
        news.forEach(repository::save);

        return news;
    }

    private int countRemainingNewsForToday(int batchSize) {
        int fetchedCount = repository.countNewsStartingFrom(LocalDate.now().atStartOfDay());
        return batchSize - fetchedCount;
    }

}
