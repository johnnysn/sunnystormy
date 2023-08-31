package com.uriel.sunnystormy.remote.news;

import com.uriel.sunnystormy.remote.news.NewsAPIOrgRequestHandler;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

class NewsAPIOrgRequestHandlerTest {

    public static MockWebServer mockBackEnd;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    private NewsAPIOrgRequestHandler subject;

    private String testResponse;

    @BeforeEach
    void initialize() throws IOException, URISyntaxException {
        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        subject = new NewsAPIOrgRequestHandler(baseUrl, "saddasdasdasd");

        File jsonFile = new File(getClass().getClassLoader().getResource("remote/news-api-org.json").toURI());
        var path = Paths.get(jsonFile.getAbsolutePath());
        testResponse = Files.readAllLines(path).stream().collect(Collectors.joining("\n"));
    }

    @Test
    void shouldFetchCorrectly() throws InterruptedException {
        // arrange
        int pageSize = 5;
        mockBackEnd.enqueue(new MockResponse()
                .setBody(testResponse)
                .addHeader("Content-Type", "application/json"));
        // act
        var result = subject.fetch(pageSize);
        // assert
        Assertions.assertEquals(2, result.size());
        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        Assertions.assertEquals("GET", recordedRequest.getMethod());
    }
}