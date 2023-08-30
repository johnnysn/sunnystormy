package com.uriel.sunnystormy.configuration;

import com.uriel.sunnystormy.remote.chat.ChatAPIRequestHandler;
import com.uriel.sunnystormy.remote.chat.DummyAIRequestHandler;
import com.uriel.sunnystormy.remote.chat.OpenAIRequestHandler;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ChatAPIBeanProvider {

    @Value("${application.chat.api-key}")
    @Setter
    private String apiKey;

    @Bean
    @Profile("prod")
    public ChatAPIRequestHandler chatAPIRequestHandler() {
        var url = "https://api.openai.com/v1";

        return new OpenAIRequestHandler(url, apiKey);
    }

    @Bean
    @Profile("!prod")
    public ChatAPIRequestHandler dummyChatAPIRequestHandler() {
        return new DummyAIRequestHandler();
    }

}
