package com.uriel.sunnystormy.service.flavored;

import com.uriel.sunnystormy.data.entity.FlavoredNews;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FlavoredNewsChatResquestBuilderTest {

    @InjectMocks
    private FlavoredNewsChatResquestBuilder subject;

    @Test
    void shouldBuildStormyRequestCorrectly() {
        // arrange
        String title = "Title";
        String content = "Content";
        FlavoredNews.Flavor flavor = FlavoredNews.Flavor.STORMY;
        // act
        String result = subject.build(title, content, flavor);
        // assert
        assertTrue(result.contains(title));
        assertTrue(result.contains(content));
        assertTrue(result.contains("pessimistic"));
    }

    @Test
    void shouldBuildSunnyRequestCorrectly() {
        // arrange
        String title = "Title II";
        String content = "Content II";
        FlavoredNews.Flavor flavor = FlavoredNews.Flavor.SUNNY;
        // act
        String result = subject.build(title, content, flavor);
        // assert
        assertTrue(result.contains(title));
        assertTrue(result.contains(content));
        assertTrue(result.contains("optimistic"));
    }
}