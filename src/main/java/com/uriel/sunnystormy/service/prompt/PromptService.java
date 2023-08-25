package com.uriel.sunnystormy.service.prompt;

import com.uriel.sunnystormy.data.entity.Prompt;
import com.uriel.sunnystormy.data.repository.PromptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromptService {

    private final PromptRepository repository;
    private final RetrieveChatResponseService retrieveChatResponseService;

    public Page<Prompt> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Prompt retrieveChatResponse(String message) {
        return retrieveChatResponseService.execute(message);
    }
}
