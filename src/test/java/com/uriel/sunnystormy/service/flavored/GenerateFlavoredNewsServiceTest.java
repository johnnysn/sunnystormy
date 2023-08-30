package com.uriel.sunnystormy.service.flavored;

import com.uriel.sunnystormy.data.entity.FlavoredNews;
import com.uriel.sunnystormy.data.entity.News;
import com.uriel.sunnystormy.data.entity.Prompt;
import com.uriel.sunnystormy.data.repository.FlavoredNewsRepository;
import com.uriel.sunnystormy.service.prompt.PromptService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenerateFlavoredNewsServiceTest {

    @Mock
    private PromptService promptService;
    @Mock
    private FlavoredNewsChatResquestBuilder flavoredNewsChatResquestBuilder;
    @Mock
    private FlavoredNewsChatResponseExtractor flavoredNewsChatResponseExtractor;
    @Mock
    private FlavoredNewsRepository repository;

    @InjectMocks
    private GenerateFlavoredNewsService subject;

    @Test
    void shouldGenerateCorrectly() {
        // arrange
        News news = getNews();
        FlavoredNews.Flavor flavor = FlavoredNews.Flavor.STORMY;
        var mockMessage = "Please, consider this";
        when(flavoredNewsChatResquestBuilder.build(news.getTitle(), news.getContent(), flavor))
                .thenReturn(mockMessage);
        var mockResponse = "{\"title\": \"Stormy title\", \"description\": \"Stormy description\"}";
        var dummyPrompt = Prompt.builder().content(mockMessage).response(mockResponse).build();
        when(promptService.retrieveChatResponse(mockMessage)).thenReturn(dummyPrompt);
        var dummyResponse = new FlavoredNewsChatResponseExtractor.Response("Stormy title", "Stormy description");
        when(flavoredNewsChatResponseExtractor.extract(mockResponse)).thenReturn(dummyResponse);
        // act
        var result = subject.execute(news, flavor);
        // assert
        assertEquals(dummyResponse.title(), result.getFlavoredTitle());
        assertEquals(dummyResponse.description(), result.getFlavoredContent());
        assertSame(news, result.getOriginalNews());
        assertSame(dummyPrompt, result.getPrompt());
        assertEquals(flavor, result.getFlavor());
        verify(repository).save(result);
    }

    @Test
    void shouldThrowException_ifSomethingIsWrongWithPrompt() {
        // arrange
        News news = getNews();
        FlavoredNews.Flavor flavor = FlavoredNews.Flavor.SUNNY;
        var mockMessage = "Please, also consider this";
        when(flavoredNewsChatResquestBuilder.build(news.getTitle(), news.getContent(), flavor))
                .thenReturn(mockMessage);
        when(promptService.retrieveChatResponse(mockMessage)).thenReturn(null);
        // act & assert
        assertThrows(ResponseStatusException.class, () -> subject.execute(news, flavor));
    }

    private News getNews() {
        return News.builder()
                .title("News title")
                .content("News content")
                .build();
    }
}