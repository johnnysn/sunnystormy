package com.uriel.sunnystormy.remote.chat;

import com.uriel.sunnystormy.data.entity.Prompt;

import java.time.LocalDateTime;

public class DummyAIRequestHandler implements ChatAPIRequestHandler {

    @Override
    public Prompt prompt(String message) {
        return Prompt.builder()
                .timestamp(LocalDateTime.now())
                .content(message)
                .response("I'm a mock AI who answered your request quite properly")
                .build();
    }

}
