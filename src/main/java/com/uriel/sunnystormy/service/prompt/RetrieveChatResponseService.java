package com.uriel.sunnystormy.service.prompt;

import com.uriel.sunnystormy.data.entity.Prompt;
import com.uriel.sunnystormy.data.repository.PromptRepository;
import com.uriel.sunnystormy.remote.ChatAPIRequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RetrieveChatResponseService {

    private final PromptRepository promptRepository;
    private final ChatAPIRequestHandler requestHandler;

    public Prompt execute(String message) {
        var prompt = requestHandler.prompt(message);

        if (prompt != null) {
            promptRepository.save(prompt);
        }

        return prompt;
    }
}
