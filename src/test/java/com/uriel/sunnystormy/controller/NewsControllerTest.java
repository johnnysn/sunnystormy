package com.uriel.sunnystormy.controller;

import com.uriel.sunnystormy.Application;
import com.uriel.sunnystormy.remote.NewsAPIOrgRequestHandler;
import com.uriel.sunnystormy.remote.NewsAPIRequestHandler;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
//@AutoConfigureTestDatabase
@TestPropertySource(properties = {"spring.main.allow-bean-definition-overriding=true"})
@ContextConfiguration(classes = {
        Application.class,
        NewsControllerTest.TestConfig.class
})
class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    public static MockWebServer mockBackEnd;

    @Value("${application.security.api-key}")
    private String apiKey;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Configuration
    public static class TestConfig {
        @Bean
        NewsAPIRequestHandler mockRequestHandler() {
            String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
            return new NewsAPIOrgRequestHandler(baseUrl, "saddasdasdasd");
        }
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:db/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:db/news.sql", executionPhase = BEFORE_TEST_METHOD),
    })
    void shouldRetrieveLastNews() throws Exception {
        this.mockMvc.perform(get("/public/news"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.content.[0].title").value("Neutral news"))
                .andExpect(jsonPath("$.content.[1].title").value("On the fence News"))
        ;
    }

    @Test
    void shouldFetchLatestNews() throws Exception {
        // arrange
        File jsonFile = new File(getClass().getClassLoader().getResource("remote/news-api-org.json").toURI());
        var path = Paths.get(jsonFile.getAbsolutePath());
        String testResponse = Files.readAllLines(path).stream().collect(Collectors.joining("\n"));
        mockBackEnd.enqueue(new MockResponse()
                .setBody(testResponse)
                .addHeader("Content-Type", "application/json"));
        // act & assert
        this.mockMvc.perform(post("/fetching/news").header("ApiKey", apiKey))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
        ;
    }
}