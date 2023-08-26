package com.uriel.sunnystormy.configuration;

import com.uriel.sunnystormy.remote.ChatAPIRequestHandler;
import com.uriel.sunnystormy.remote.OpenAIRequestHandler;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatAPIBeanProvider {

    @Value("${chat.api-key}")
    @Setter
    private String apiKey;

    @Bean
    public ChatAPIRequestHandler chatAPIRequestHandler() {
        var url = "https://api.openai.com/v1";

        return new OpenAIRequestHandler(url, apiKey);
    }

}
