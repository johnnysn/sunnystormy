package com.uriel.sunnystormy.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class FlavoredNewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${application.security.api-key}")
    private String apiKey;

    @Test
    @SqlGroup({
            @Sql(value = "classpath:db/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:db/flavored-news.sql", executionPhase = BEFORE_TEST_METHOD),
    })
    void shouldRetrieveLastNews() throws Exception {
        this.mockMvc.perform(get("/public/flavored-news"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.content.[0].flavoredTitle").value("Good News"))
                .andExpect(jsonPath("$.content.[0].flavor").value("SUNNY"))
                .andExpect(jsonPath("$.content.[1].flavoredTitle").value("Bad News"))
                .andExpect(jsonPath("$.content.[1].flavor").value("STORMY"))
        ;
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:db/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:db/news.sql", executionPhase = BEFORE_TEST_METHOD),
    })
    void shouldCreateFromPrompting() throws Exception {
        final String news_id = "49ef2c30-3ddd-11ee-be56-0242ac120002";
        this.mockMvc.perform(
                    post("/prompting/flavored-news")
                            .header("ApiKey", apiKey)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"news_id\": \""+ news_id +"\", \"flavor\": \"SUNNY\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flavor").value("SUNNY"))
                .andExpect(jsonPath("$.originalNews.id").value(news_id))
        ;
    }

    @Test
    @SqlGroup({
            @Sql(value = "classpath:db/reset.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = "classpath:db/flavored-news.sql", executionPhase = BEFORE_TEST_METHOD),
    })
    void shouldRespondWithErrorIfFlavoredNewsAlreadyExists() throws Exception {
        final String news_id = "874fc980-4054-11ee-be56-0242ac120002";
        this.mockMvc.perform(
                        post("/prompting/flavored-news")
                                .header("ApiKey", apiKey)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"news_id\": \""+ news_id +"\", \"flavor\": \"STORMY\"}")
                )
                .andExpect(status().isBadRequest())
        ;
    }
}
