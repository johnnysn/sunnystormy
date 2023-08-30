package com.uriel.sunnystormy.service.flavored;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class FlavoredNewsChatResponseExtractor {

    public Response extract(String response) {
        var mapper = new ObjectMapper();

        try {
            return mapper.readValue(response, Response.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not process Chat Response properly", e);
        }
    }

    public record Response(String title, String description) {}
}
