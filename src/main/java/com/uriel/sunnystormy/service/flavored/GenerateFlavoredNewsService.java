package com.uriel.sunnystormy.service.flavored;

import com.uriel.sunnystormy.data.entity.FlavoredNews;
import com.uriel.sunnystormy.data.entity.News;
import com.uriel.sunnystormy.data.entity.Prompt;
import com.uriel.sunnystormy.data.repository.FlavoredNewsRepository;
import com.uriel.sunnystormy.service.prompt.PromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
class GenerateFlavoredNewsService {

    private final PromptService promptService;
    private final FlavoredNewsChatResquestBuilder flavoredNewsChatResquestBuilder;
    private final FlavoredNewsChatResponseExtractor flavoredNewsChatResponseExtractor;
    private final FlavoredNewsRepository repository;

    public FlavoredNews execute(News news, FlavoredNews.Flavor flavor) {
        String message = flavoredNewsChatResquestBuilder.build(news.getTitle(), news.getContent(), flavor);

        var prompt = getChatResponse(message);
        var flavoredNews = buildFlavoredNews(news, prompt, flavor);

        repository.save(flavoredNews);
        return flavoredNews;
    }

    private FlavoredNews buildFlavoredNews(News news, Prompt prompt, FlavoredNews.Flavor flavor) {
        var response = flavoredNewsChatResponseExtractor.extract(prompt.getResponse());

        return FlavoredNews.builder()
                .originalNews(news)
                .prompt(prompt)
                .flavor(flavor)
                .flavoredTitle(response.title())
                .flavoredContent(response.description())
                .build();
    }

    private Prompt getChatResponse(String message) {
        var prompt = promptService.retrieveChatResponse(message);
        if (prompt == null) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Could not obtain a response from the Chat AI");
        }
        return prompt;
    }
}
