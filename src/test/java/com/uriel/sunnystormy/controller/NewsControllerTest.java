package com.uriel.sunnystormy.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

}