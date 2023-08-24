package com.uriel.sunnystormy.config;

import com.uriel.sunnystormy.remote.NewsAPIOrgRequestHandler;
import com.uriel.sunnystormy.remote.NewsAPIRequestHandler;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NewsAPIBeanProvider {

    @Value("${news.api-key}")
    @Setter
    private String apiKey;

    @Bean
    public NewsAPIRequestHandler newsAPIRequestHandler() {
        var url = "https://newsapi.org/v2";

        return new NewsAPIOrgRequestHandler(url, apiKey);
    }

}
