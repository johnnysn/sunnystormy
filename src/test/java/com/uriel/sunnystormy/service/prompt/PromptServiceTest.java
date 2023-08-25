package com.uriel.sunnystormy.service.prompt;

import com.uriel.sunnystormy.data.entity.Prompt;
import com.uriel.sunnystormy.data.repository.PromptRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PromptServiceTest {

    @Mock
    private PromptRepository repository;

    @InjectMocks
    private PromptService service;

    @Test
    void mustFindAllCorrectly() {
        // arrange
        var prompt1 = Prompt.builder()
                .content("Please, rewrite this article with grape flavor")
                .response("Sure, I can do that! I'm a robot!")
                .timestamp(LocalDateTime.now())
                .build();
        var prompt2 = Prompt.builder()
                .content("Please, rewrite this article with orange flavor")
                .response("Right on it! I'm AI!")
                .timestamp(LocalDateTime.now())
                .build();
        var pageable = PageRequest.of(0, 5);
        when(repository.findAll(any(Pageable.class))).thenReturn(
                new PageImpl<>(List.of(prompt1, prompt2), pageable, 2)
        );
        // act
        var page = service.findAll(pageable);
        // assert
        Assertions.assertTrue(page.getContent().contains(prompt1));
        Assertions.assertTrue(page.getContent().contains(prompt2));
        Assertions.assertEquals(2, page.getTotalElements());
    }
}
