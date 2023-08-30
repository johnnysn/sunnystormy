package com.uriel.sunnystormy.service.flavored;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FlavoredNewsChatResponseExtractorTest {

    @InjectMocks
    private FlavoredNewsChatResponseExtractor subject;

    @Test
    void shouldExtractCorrectly() {
        // arrange
        String response = "{\"title\": \"My title\", \"description\": \"My description\"}";
        // act
        var result = subject.extract(response);
        // assert
        assertEquals("My title", result.title());
        assertEquals("My description", result.description());
    }

    @Test
    void shouldThrowException_ifJsonCannotBeParsedToResponse() {
        // arrange
        String response = "{\"ridiculousKey\": \"Idiotic value\"}";
        // act & assert
        assertThrows(RuntimeException.class, () -> subject.extract(response));
    }
}