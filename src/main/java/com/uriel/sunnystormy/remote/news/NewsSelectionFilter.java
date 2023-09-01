package com.uriel.sunnystormy.remote.news;

import com.uriel.sunnystormy.configuration.NewsProperties;
import com.uriel.sunnystormy.data.entity.News;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class NewsSelectionFilter {

    private final NewsProperties newsProperties;

    public List<News> select(Stream<News> newsStream, int batchSize) {
        return newsStream
                .filter(
                        n -> n.getContent().split("\\s+").length <= newsProperties.maxWordCount()
                )
                .sorted(Comparator.comparingInt(n -> ((News) n).getContent().length()).reversed())
                .limit(batchSize)
                .toList();
    }

}
