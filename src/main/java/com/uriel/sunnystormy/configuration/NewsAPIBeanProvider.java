package com.uriel.sunnystormy.configuration;

import com.uriel.sunnystormy.remote.NewsAPIOrgRequestHandler;
import com.uriel.sunnystormy.remote.NewsAPIRequestHandler;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class NewsAPIBeanProvider {

    @Value("${application.news.api-key}")
    @Setter
    private String apiKey;

    @Bean
    public NewsAPIRequestHandler newsAPIRequestHandler() {
        var url = "https://newsapi.org/v2";

        return new NewsAPIOrgRequestHandler(url, apiKey);
    }

}
