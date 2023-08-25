package com.uriel.sunnystormy.remote;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

class OpenAIRequestHandlerTest {

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

    private OpenAIRequestHandler subject;

    private String testResponse;

    @BeforeEach
    void initialize() throws IOException, URISyntaxException {
        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        subject = new OpenAIRequestHandler(baseUrl, "saddasdasdasd");

        File jsonFile = new File(getClass().getClassLoader().getResource("remote/openai-api.json").toURI());
        var path = Paths.get(jsonFile.getAbsolutePath());
        testResponse = Files.readAllLines(path).stream().collect(Collectors.joining("\n"));
    }

    @Test
    void shouldRequestCorrectly() throws InterruptedException {
        // arrange
        String message = "Please, be nice with me!";
        mockBackEnd.enqueue(new MockResponse()
                .setBody(testResponse)
                .addHeader("Content-Type", "application/json"));
        // act
        var result = subject.prompt(message);
        // assert
        Assertions.assertEquals(message, result.getContent());
        Assert.assertNotNull(result.getResponse());
        Assert.assertNotNull(result.getTimestamp());
        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        Assertions.assertEquals("POST", recordedRequest.getMethod());
        Assertions.assertEquals("/chat/completions", recordedRequest.getPath());
    }

}