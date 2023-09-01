package com.uriel.sunnystormy.remote.news;

import com.uriel.sunnystormy.data.entity.News;
import com.uriel.sunnystormy.remote.news.dto.NewsAPIOrgDTO;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

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

    private NewsSelectionFilter selectionFilter;
    private NewsAPIOrgMapper mapper;
    private NewsAPIOrgRequestHandler subject;

    private String testResponse;

    @BeforeEach
    void initialize() throws IOException, URISyntaxException {
        selectionFilter = mock(NewsSelectionFilter.class);
        mapper = mock(NewsAPIOrgMapper.class);
        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        subject = new NewsAPIOrgRequestHandler(baseUrl, "saddasdasdasd");
        subject.setMapper(mapper);
        subject.setSelectionFilter(selectionFilter);

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
        var news1 = News.builder().build();
        var news2 = News.builder().build();
        when(mapper.map(any(NewsAPIOrgDTO.Article.class)))
                .thenReturn(news1).thenReturn(news2);
        when(selectionFilter.select(any(Stream.class), anyInt())).thenReturn(
                List.of(news1, news2)
        );
        // act
        var result = subject.fetch(pageSize);
        // assert
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(news1));
        Assertions.assertTrue(result.contains(news2));
        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        Assertions.assertEquals("GET", recordedRequest.getMethod());
    }
}