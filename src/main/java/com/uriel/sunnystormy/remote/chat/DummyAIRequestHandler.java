package com.uriel.sunnystormy.remote.chat;

import com.uriel.sunnystormy.data.entity.Prompt;

import java.time.LocalDateTime;

public class DummyAIRequestHandler implements ChatAPIRequestHandler {

    @Override
    public Prompt prompt(String message) {
        return Prompt.builder()
                .timestamp(LocalDateTime.now())
                .content(message)
                .response("""
                        {
                        "title": "My dummy flavored title",
                        "description": "My dummy flavored description"
                        }
                        """)
                .build();
    }

}
