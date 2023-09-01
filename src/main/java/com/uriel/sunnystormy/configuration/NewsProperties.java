package com.uriel.sunnystormy.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.news")
public record NewsProperties(int maxPageSize, int maxBatchSize, String apiKey, int maxWordCount) {
}
