package com.uriel.sunnystormy.service.prompt;

import com.uriel.sunnystormy.data.entity.Prompt;
import com.uriel.sunnystormy.data.repository.PromptRepository;
import com.uriel.sunnystormy.remote.chat.ChatAPIRequestHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RetrieveChatResponseServiceTest {

    @Mock
    private PromptRepository repository;
    @Mock
    private ChatAPIRequestHandler requestHandler;

    @InjectMocks
    private RetrieveChatResponseService subject;

    @Test
    void shouldRetrievePromptCorrectly() {
        // arrange
        String message = "Please, complete this";
        var prompt = Prompt.builder().response("Sample response").build();
        Mockito.when(requestHandler.prompt(message)).thenReturn(prompt);
        // act
        var result = subject.execute(message);
        // assert
        assertSame(prompt, result);
        Mockito.verify(repository).save(result);
    }
}